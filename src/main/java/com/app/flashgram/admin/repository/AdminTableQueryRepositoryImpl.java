package com.app.flashgram.admin.repository;

import com.app.flashgram.admin.ui.dto.GetTableListResponse;
import com.app.flashgram.admin.ui.dto.comments.GetCommentTableRequestDto;
import com.app.flashgram.admin.ui.dto.comments.GetCommentTableResponseDto;
import com.app.flashgram.admin.ui.dto.posts.GetPostTableRequestDto;
import com.app.flashgram.admin.ui.dto.posts.GetPostTableResponseDto;
import com.app.flashgram.admin.ui.dto.users.GetUserTableRequestDto;
import com.app.flashgram.admin.ui.dto.users.GetUserTableResponseDto;
import com.app.flashgram.admin.ui.query.AdminTableQueryRepository;
import com.app.flashgram.auth.repositiry.entity.QUserAuthEntity;
import com.app.flashgram.post.repository.entity.comment.QCommentEntity;
import com.app.flashgram.post.repository.entity.post.QPostEntity;
import com.app.flashgram.user.repository.entity.QUserEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * 관리자 테이블 조회를 위한 QueryDSL 레포지토리 구현체
 * 유저 정보와 인증 정보, 게시글 및 댓글 정보를 관리자 화면에 표시할 수 있도록 데이터 조회
 */
@Repository
@RequiredArgsConstructor
public class AdminTableQueryRepositoryImpl implements AdminTableQueryRepository {

    private final JPAQueryFactory queryFactory;
    private static final QUserAuthEntity userAuthEntity = QUserAuthEntity.userAuthEntity;
    private static final QUserEntity userEntity = QUserEntity.userEntity;
    private static final QPostEntity postEntity = QPostEntity.postEntity;
    private static final QCommentEntity commentEntity = QCommentEntity.commentEntity;


    /**
     * 유저 테이블 데이터를 페이징하여 조회
     * 유저 기본 정보(UserEntity)와 인증 정보(UserAuthEntity)를 조인하여 결를 반환
     *
     * @param dto 페이징 및 검색 조건을 포함한 요청 DTO
     * @return GetTableListResponse 전체 데이터 수와 페이징된 유저 목록
     */
    @Override
    public GetTableListResponse<GetUserTableResponseDto> getUserTableData(
            GetUserTableRequestDto dto) {

        int total = queryFactory
                .select(userEntity.id)
                .from(userEntity)
                .where(likeName(dto.getName()))
                .fetch()
                .size();

        /*
        List<GetUserTableResponseDto> result = queryFactory
                .select(
                        Projections.fields(
                                GetUserTableResponseDto.class,
                                userEntity.id.as("id"),
                                userAuthEntity.email.as("email"),
                                userEntity.name.as("name"),
                                userAuthEntity.role.as("role"),
                                userEntity.regAt.as("regAt"),
                                userEntity.updAt.as("updAt"),
                                userAuthEntity.lastLoginDt.as("lastLoginDt")
                        )
                )
                .from(userEntity)
                .join(userAuthEntity).on(userAuthEntity.userId.eq(userEntity.id))
                .where(likeName(dto.getName()))
                .orderBy(userEntity.id.desc())
                .offset(dto.getOffset())
                .limit(dto.getLimit())
                .fetch();

        return new GetTableListResponse<>(total, result);
        */

        List<Long> ids = queryFactory
                .select(userEntity.id)
                .from(userEntity)
                .where(
                        likeName(dto.getName())
                )
                .orderBy(userEntity.id.desc())
                .offset(dto.getOffset())
                .limit(dto.getLimit())
                .fetch();

        List<GetUserTableResponseDto> result = queryFactory
                .select(
                        Projections.fields(
                                GetUserTableResponseDto.class,
                                userEntity.id.as("id"),
                                userAuthEntity.email.as("email"),
                                userEntity.name.as("name"),
                                userAuthEntity.role.as("role"),
                                userEntity.regAt.as("regAt"),
                                userEntity.updAt.as("updAt"),
                                userAuthEntity.lastLoginDt.as("lastLoginDt")
                        )

                )
                .from(userEntity)
                .join(userAuthEntity).on(userAuthEntity.userId.eq(userEntity.id))
                .where(userEntity.id.in(ids))
                .orderBy(userEntity.id.desc())
                .fetch();

        return new GetTableListResponse<>(total, result);
    }

    /**
     * 유저 이름으로 검색하기 위한 동적 검색 조건 생성
     *
     * @param name 검색할 유저 이름 (null이거나 공백인 경우 검색 조건 미적용)
     * @return BooleanExpression 이름 검색을 위한 QueryDSL 표현식
     *         - name이 null이거나 공백인 경우: null 반환
     *         - 그 외의 경우: 이름이 주어진 문자열로 시작하는 조건 반환
     */
    private BooleanExpression likeName(String name) {
        if (name == null || name.isBlank()){

            return null;
        }

        return userEntity.name.like(name + "%");
    }

    /**
     * 게시글 테이블 데이터를 페이징하여 조회
     *
     * @param dto 페이징 및 검색 조건을 포함한 요청 DTO
     * @return GetTableListResponse 전체 데이터 수와 페이징된 게시글 목록
     */
    @Override
    public GetTableListResponse<GetPostTableResponseDto> getPostTableData(
            GetPostTableRequestDto dto) {
        int total = queryFactory
                .select(postEntity.id)
                .from(postEntity)
                .where(
                        eqPostId(dto.getPostId())
                )
                .fetch()
                .size();

        List<Long> ids = queryFactory
                .select(postEntity.id)
                .from(postEntity)
                .where(
                        eqPostId(dto.getPostId())
                )
                .orderBy(postEntity.id.desc())
                .offset(dto.getOffset())
                .limit(dto.getLimit())
                .fetch();

        List<GetPostTableResponseDto> result = queryFactory
                .select(
                        Projections.fields(
                                GetPostTableResponseDto.class,
                                postEntity.id.as("postId"),
                                userEntity.id.as("userId"),
                                userEntity.name.as("userName"),
                                postEntity.content.as("content"),
                                postEntity.regAt.as("regAt"),
                                postEntity.updAt.as("updAt")
                        )
                )
                .from(postEntity)
                .join(userEntity).on(postEntity.author.id.eq(userEntity.id))
                .where(
                        postEntity.id.in(ids)
                )
                .orderBy(postEntity.id.desc())
                .fetch();

        return new GetTableListResponse<>(total, result);
    }

    /**
     * 게시글 ID에 대한 동적 검색 조건 생성
     *
     * @param postId 검색할 게시글 ID (null인 경우 검색 조건 미적용)
     * @return BooleanExpression 게시글 ID 검색을 위한 QueryDSL 표현식
     */
    private BooleanExpression eqPostId(Long postId) {
        if (postId == null){

            return null;
        }

        return postEntity.id.eq(postId);
    }

    /**
     * 댓글 테이블 데이터를 페이징하여 조회
     *
     * @param dto 페이징 및 검색 조건을 포함한 요청 DTO
     * @return GetTableListResponse 전체 데이터 수와 페이징된 댓글 목록
     */
    @Override
    public GetTableListResponse<GetCommentTableResponseDto> getCommentTableData(
            GetCommentTableRequestDto dto) {
        int total = queryFactory
                .select(commentEntity.id)
                .from(commentEntity)
                .join(postEntity).on(commentEntity.post.id.eq(postEntity.id))
                .where(
                        eqCommentId(dto.getCommentId()),
                        eqPostId(dto.getPostId())
                )
                .fetch()
                .size();

        List<Long> ids = queryFactory
                .select(commentEntity.id)
                .from(commentEntity)
                .join(postEntity).on(commentEntity.post.id.eq(postEntity.id))
                .where(
                        eqCommentId(dto.getCommentId()),
                        eqPostId(dto.getPostId())
                )
                .orderBy(commentEntity.id.desc())
                .offset(dto.getOffset())
                .limit(dto.getLimit())
                .fetch();

        List<GetCommentTableResponseDto> result = queryFactory
                .select(
                        Projections.fields(
                                GetCommentTableResponseDto.class,
                                commentEntity.id.as("commentId"),
                                userEntity.id.as("userId"),
                                userEntity.name.as("userName"),
                                postEntity.id.as("postId"),
                                commentEntity.content.as("content"),
                                commentEntity.regAt.as("regAt"),
                                commentEntity.updAt.as("updAt")
                        )
                )
                .from(commentEntity)
                .join(userEntity).on(commentEntity.author.id.eq(userEntity.id))
                .join(postEntity).on(commentEntity.post.id.eq(postEntity.id))
                .where(
                        commentEntity.id.in(ids)
                )
                .orderBy(commentEntity.id.desc())
                .fetch();

        return new GetTableListResponse<>(total, result);
    }

    /**
     * 댓글 ID에 대한 동적 검색 조건 생성
     *
     * @param commentId 검색할 댓글 ID (null인 경우 검색 조건 미적용)
     * @return BooleanExpression 댓글 ID 검색을 위한 QueryDSL 표현식
     */
    private BooleanExpression eqCommentId(Long commentId) {
        if (commentId == null){

            return null;
        }

        return commentEntity.id.eq(commentId);
    }
}
