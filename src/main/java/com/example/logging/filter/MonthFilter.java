package com.example.logging.filter;

import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Month filter
 */
public class MonthFilter extends TimePeriodFilter {
    public MonthFilter(String parameter) {
        super(parameter);
    }

    @Override
    public boolean test(String line) {
        Pair<LocalDateTime, LocalDateTime> timeValues = getTimeValues(line);
        ZoneId zoneId = ZoneId.systemDefault();

        LocalDateTime parameterDate = timeValues.getLeft();
        LocalDateTime logMessageDate = timeValues.getRight();

        long parameterMonth = parameterDate.toLocalDate().withDayOfMonth(1).atTime(0,0).atZone(zoneId).toEpochSecond();
        long logMessageMonth = logMessageDate.toLocalDate().withDayOfMonth(1).atTime(0,0).atZone(zoneId).toEpochSecond();

        return parameterMonth == logMessageMonth;
    }
}
