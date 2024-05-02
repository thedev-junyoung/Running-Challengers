package com.example.runners.error;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExceptionHandlingAspect {
    @AfterThrowing(pointcut = "execution(* com.example.runners..*(..))", throwing = "ex")
    public void handleExceptions(RuntimeException ex) {
        // 로그 출력, 메트릭스 업데이트, 특정 작업 수행 등
        System.out.println("Exception caught in AOP: " + ex.getMessage());
        // 여기서는 실제로 예외를 '처리'하지 않고, 로그만 출력합니다.
        // 실제 응답은 ControllerAdvice를 통해 처리됩니다.
    }
}