package com.app.flashgram.common.idempotency;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 멱등성을 보장해야 하는 메서드에 표시하는 어노테이션
 * 이 어노테이션이 적용된 메서드는 동일한 요청이 여러 번 실행되더라도
 * 부작용 없이 항상 동일한 결과를 반환하도록 보장
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Idempotent {


}
