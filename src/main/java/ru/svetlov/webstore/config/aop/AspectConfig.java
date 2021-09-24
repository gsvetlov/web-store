package ru.svetlov.webstore.config.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@Aspect
@Slf4j
public class AspectConfig {

    @Pointcut(value = "execution(public * ru.svetlov.webstore.service.*.*(..))")
    public void servicesTrackerPointcut() {

    }

    @Pointcut(value = "execution(public * ru.svetlov.webstore.api.v1.controller.*.*(..))")
    public void controllerTrackerPointcut() {

    }

    @Around(value = "servicesTrackerPointcut()")
    public Object beforeServicesTracker(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long start = System.nanoTime();
        Object result = proceedingJoinPoint.proceed();
        LocalTime time = LocalTime.ofNanoOfDay(System.nanoTime() - start);
        writeLog("Service call: ", proceedingJoinPoint.getSignature().toShortString(), " execution time: ", time);
        return result;
    }

    @Around(value = "controllerTrackerPointcut()")
    public Object aroundControllerTracker(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long start = System.nanoTime();
        Object result = proceedingJoinPoint.proceed();
        LocalTime time = LocalTime.ofNanoOfDay(System.nanoTime() - start);
        writeLog("Controller request: ", proceedingJoinPoint.getSignature().toShortString(), " execution time: ", time);
        return result;
    }

    private final StringBuilder sb = new StringBuilder();

    private void writeLog(Object... args) {
        sb.setLength(0);
        for (Object o : args) {
            sb.append(o.toString());
        }
        log.info(sb.toString());
    }
}
