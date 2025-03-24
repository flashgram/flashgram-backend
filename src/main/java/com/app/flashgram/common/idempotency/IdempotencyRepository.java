package com.app.flashgram.common.idempotency;

public interface IdempotencyRepository {

    Idempotency getByKey(String key);
    void save(Idempotency idempotency);
}
