package ru.svetlov.webstore.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class StatisticsRecord {
    private final String key;
    private final String value;

    public static StatisticsRecord of(String key, String value) {
        return new StatisticsRecord(key, value);
    }

    private StatisticsRecord(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
