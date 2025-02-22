package com.app.flashgram.admin.repository;

import com.app.flashgram.admin.ui.dto.GetTableListResponse;
import com.app.flashgram.admin.ui.dto.Users.GetUserTableRequestDto;
import com.app.flashgram.admin.ui.dto.Users.GetUserTableResponseDto;
import com.app.flashgram.admin.ui.query.AdminTableQueryRepository;
import com.app.flashgram.auth.repositiry.entity.QUserAuthEntity;
import com.app.flashgram.user.repository.entity.QUserEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * 관리자 테이블 조회를 위한 QueryDSL 레포지토리 구현체
 * 유저 정보와 인증 정보를 조합하여 관리자 화면에 표시할 데이터 조회
 */
@Repository
@RequiredArgsConstructor
public class AdminTableQueryRepositoryImpl implements AdminTableQueryRepository {

    private final JPAQueryFactory queryFactory;
    private static final QUserAuthEntity userAuthEntity = QUserAuthEntity.userAuthEntity;
    private static final QUserEntity userEntity = QUserEntity.userEntity;


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
}
