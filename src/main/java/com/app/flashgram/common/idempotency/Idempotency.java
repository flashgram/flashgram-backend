package com.app.flashgram.common.idempotency;

import com.app.flashgram.common.ui.Response;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * 멱등성 작업을 나타내는 클래스로, 작업의 키와 응답 포함
 * 멱등성 요청의 결과를 저장하고 추적하는데 사용
 */
@Getter
@AllArgsConstructor
public class Idempotency {

    private final String key;
    private final Response<?> response;
}
