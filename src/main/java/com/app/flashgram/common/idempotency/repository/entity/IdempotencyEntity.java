package com.app.flashgram.common.idempotency.repository.entity;

import com.app.flashgram.common.idempotency.Idempotency;
import com.app.flashgram.common.utils.ResponseObjectMapper;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 멱등성 정보를 저장하기 위한 엔티티 클래스
 */
@Entity
@Table(name = "fg_idempotency")
@NoArgsConstructor
@AllArgsConstructor
public class IdempotencyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String idempotencyKey;

    @Getter
    @Column(nullable = false)
    private String response;

    /**
     * Idempotency 객체로부터 엔티티 생성
     *
     * @param idempotency 변환할 Idempotency 객체
     */
    public IdempotencyEntity(Idempotency idempotency) {
        this.idempotencyKey = idempotency.getKey();
        this.response = ResponseObjectMapper.toStringResponse(idempotency.getResponse());
    }

    /**
     * 엔티티를 Idempotency 객체로 변환
     *
     * @return 변환된 Idempotency 객체
     */
    public Idempotency toIdempotency() {

        return new Idempotency(this.idempotencyKey, ResponseObjectMapper.toResponseObject(response));
    }
}
