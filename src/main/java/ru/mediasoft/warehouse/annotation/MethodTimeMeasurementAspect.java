package ru.mediasoft.warehouse.annotation;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Aspect
@Component
public class MethodTimeMeasurementAspect {

    @Around("@annotation(MeasureTime)")
    public Object measureTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.nanoTime();
        try {
            return joinPoint.proceed();
        } finally {
            long endTime = System.nanoTime();
            long executionTime = endTime - startTime;
            double durationSeconds = TimeUnit.NANOSECONDS.toSeconds(executionTime);
            log.info("{} executed in {} seconds", joinPoint.getSignature(), durationSeconds);
        }
    }

}
