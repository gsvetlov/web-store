package ru.svetlov.webstore.api.dto;

import java.util.ArrayList;
import java.util.Collection;

public class StatisticsDto {
    private Collection<StatisticsRecord> records;

    public StatisticsDto() {
        records = new ArrayList<>();
    }

    public StatisticsDto(Collection<StatisticsRecord> records) {
        this.records = records;
    }

    public Collection<StatisticsRecord> getRecords() {
        return records;
    }

    public void setRecords(Collection<StatisticsRecord> records) {
        this.records = records;
    }
}

