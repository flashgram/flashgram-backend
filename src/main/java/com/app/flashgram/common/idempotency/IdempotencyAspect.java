package com.app.flashgram.common.idempotency;

import com.app.flashgram.common.ui.Response;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 멱등성을 처리하기 위한 AOP Aspect 클래스
 * {@code @Idempotent} 어노테이션이 적용된 메서드에 대해 멱등성 보장
 */
@Aspect
@Component
@RequiredArgsConstructor
public class IdempotencyAspect {

    private final IdempotencyRepository idempotencyRepository;
    private final HttpServletRequest request;

    /**
     * {@code @Idempotent} 어노테이션이 적용된 메서드의 실행을 가로채서 멱등성 검사 및 처리
     * 요청 헤더에서 'Idempotency-Key'를 확인하여 중복 요청 처리
     *
     * @param joinPoint AOP 조인 포인트
     * @return 메서드 실행 결과 또는 저장된 이전 응답
     * @throws Throwable 메서드 실행 중 발생할 수 있는 예외
     */
    @Around("@annotation(Idempotent)")
    public Object checkIdempotency(ProceedingJoinPoint joinPoint) throws Throwable {
        String idempotencyKey = request.getHeader("Idempotency-Key");

        if (idempotencyKey == null) {

            return joinPoint.proceed();
        }

        Idempotency idempotency = idempotencyRepository.getByKey(idempotencyKey);

        if (idempotency != null) {

            return idempotency.getResponse();
        }

        Object result = joinPoint.proceed();

        Idempotency newIdempotency = new Idempotency(idempotencyKey, (Response<?>) result);
        idempotencyRepository.save(newIdempotency);

        return result;
    }
}
