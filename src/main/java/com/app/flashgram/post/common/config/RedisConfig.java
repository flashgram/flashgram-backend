package com.app.flashgram.post.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis 설정을 위한 Configuration 클래스
 * RedisTemplate을 설정하여 Redis 서버와의 데이터 입출력 처리
 */
@Configuration
public class RedisConfig {

    /**
     * RedisTemplate bean 설정
     * Redis에 저장될 데이터를 직렬화하는 방법 설정
     *
     * @param connectionFactory Redis 연결 팩토리
     * @return 설정된 RedisTemplate 객체
     */
    @Bean
    public RedisTemplate<String, Long> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Long> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Long.class));

        return template;
    }
}