package com.app.flashgram.common.idempotency.repository;

import com.app.flashgram.common.idempotency.Idempotency;
import com.app.flashgram.common.idempotency.IdempotencyRepository;
import com.app.flashgram.common.idempotency.repository.entity.IdempotencyEntity;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * IdempotencyRepository 인터페이스의 구현체
 * JPA를 사용하여 멱등성 데이터를 저장 및 조회
 */
@Repository
@RequiredArgsConstructor
public class IdempotencyRepositoryImpl implements IdempotencyRepository {

    private final JpaIdempotencyRepository jpaIdempotencyRepository;

    /**
     * 주어진 키에 해당하는 멱등성 데이터 조회
     *
     * @param key 조회할 멱등성 키
     * @return 찾은 Idempotency 객체, 없을 경우 null
     */
    @Override
    public Idempotency getByKey(String key) {
        Optional<IdempotencyEntity> idempotencyEntity = jpaIdempotencyRepository.findByIdempotencyKey(key);

        return idempotencyEntity.map(IdempotencyEntity::toIdempotency).orElse(null);
    }

    /**
     * 멱등성 데이터 저장
     *
     * @param idempotency 저장할 Idempotency 객체
     */
    @Override
    public void save(Idempotency idempotency) {
        jpaIdempotencyRepository.save(new IdempotencyEntity(idempotency));
    }
}
