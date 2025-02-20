package com.app.flashgram.common.principal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 인증된 사용자 정보를 메서드 매개변수에 주입하기 위한 애너테이션
 * 이 애너테이션이 적용된 매개변수는 사용자 인증 정보를 포함한 UserPrincipal 객체로 자동으로 해석됨
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthPrincipal {

}
