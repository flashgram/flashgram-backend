package com.app.flashgram.common.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * QueryDSL 설정을 위한 Configuration 클래스
 * QueryDSL을 사용하여 타입 안전한 쿼리를 작성할 수 있도록 JPAQueryFactory 설정
 */
@Configuration
public class QueryDslConfig {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * QueryDSL의 JPAQueryFactory 빈 생성
     * 애플리케이션 전역에서 QueryDSL을 사용한 쿼리 작성에 활용
     *
     * @return 생성된 JPAQueryFactory 인스턴스
     */
    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }
}
