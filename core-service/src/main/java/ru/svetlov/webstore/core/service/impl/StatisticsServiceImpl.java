package ru.svetlov.webstore.core.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.svetlov.webstore.api.dtos.StatisticsDto;
import ru.svetlov.webstore.api.dtos.StatisticsRecord;
import ru.svetlov.webstore.core.service.StatisticsService;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Aspect
@Slf4j
@Service
public class StatisticsServiceImpl implements StatisticsService {
    private final Map<String, Long> records = new HashMap<>();
    private final StringBuilder sb = new StringBuilder();

    @Override
    public StatisticsDto getStatistics() {
        return new StatisticsDto(
                records.entrySet()
                        .stream()
                        .map(entry -> StatisticsRecord.of(entry.getKey(), LocalTime.ofNanoOfDay(entry.getValue()).toString()))
                        .collect(Collectors.toList()));
    }

    @Around(value = "execution(public * ru.svetlov.webstore.core.service.impl.*.*(..))")
    public Object serviceTracker(ProceedingJoinPoint pjp) throws Throwable {
        return measureTime(pjp, pjp.toLongString());
    }

    private Object measureTime(ProceedingJoinPoint proceedingJoinPoint, String serviceKey) throws Throwable {
        long start = System.nanoTime();
        Object result = proceedingJoinPoint.proceed();
        long time = System.nanoTime() - start;

        log.info(getLogMessage("Service call: ", proceedingJoinPoint.getSignature().toShortString(), " execution time: ", LocalTime.ofNanoOfDay(time)));

        records.merge(serviceKey, time, Long::sum);

        return result;
    }

    private String getLogMessage(Object... args) {
        sb.setLength(0);
        for (Object o : args) {
            sb.append(o.toString());
        }
        return sb.toString();
    }


}
