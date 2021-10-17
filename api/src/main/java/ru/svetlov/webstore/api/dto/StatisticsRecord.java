package ru.svetlov.webstore.api.dto;

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

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
