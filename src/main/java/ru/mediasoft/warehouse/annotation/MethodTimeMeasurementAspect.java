package ru.mediasoft.warehouse.annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class MethodTimeMeasurementAspect {

    @Around("@annotation(MeasureTime)")
    public Object measureTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.nanoTime();
        Object result = joinPoint.proceed();
        long endTime = System.nanoTime();
        long executionTime = endTime - startTime;
        double durationSeconds = TimeUnit.NANOSECONDS.toSeconds(executionTime);
        System.out.println(joinPoint.getSignature() + " executed in " + durationSeconds + " seconds");
        return result;
    }
}
