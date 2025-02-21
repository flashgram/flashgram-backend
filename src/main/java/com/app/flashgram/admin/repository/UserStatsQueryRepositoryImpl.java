package com.app.flashgram.admin.repository;

import com.app.flashgram.admin.ui.dto.GetDailyUserResponseDto;
import com.app.flashgram.admin.ui.query.UserStatsQueryRepository;
import com.app.flashgram.common.TimeCalculator;
import com.app.flashgram.user.repository.entity.QUserEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * 유저 통계 정보를 조회하는 레포지토리 구현체
 */
@Repository
@RequiredArgsConstructor
public class UserStatsQueryRepositoryImpl implements UserStatsQueryRepository {

    private final JPAQueryFactory queryFactory;
    private static final QUserEntity userEntity = QUserEntity.userEntity;

    /**
     * 지정된 일수 이전부터 현재까지의 일별 유저 등록 통계 조회
     *
     * @param beforeDays 조회할 이전 일수
     * @return 일별 유저 등록 통계 목록. 등록일자별로 오름차순 정렬
     * @see GetDailyUserResponseDto 일별 유저 등록 통계 정보를 담는 DTO
     */
    @Override
    public List<GetDailyUserResponseDto> getDailyResisterUserStats(int beforeDays) {
        return queryFactory
                .select(
                        Projections.fields(
                                GetDailyUserResponseDto.class,
                                userEntity.regDate.as("regDate"),
                                userEntity.count().as("count")
                        )
                )
                .from(userEntity)
                .where(userEntity.regDate.after(TimeCalculator.getDateDaysAgo(beforeDays)))
                .groupBy(userEntity.regDate)
                .orderBy(userEntity.regDate.asc())
                .fetch();
    }
}
