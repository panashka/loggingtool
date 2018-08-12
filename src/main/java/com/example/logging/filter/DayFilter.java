package com.example.logging.filter;


import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * filter by specific day
 */
public class DayFilter extends TimePeriodFilter {
    public DayFilter(String parameter) {
        super(parameter);
    }

    @Override
    public boolean test(String line) {
        Pair<LocalDateTime, LocalDateTime> timeValues = getTimeValues(line);
        ZoneId zoneId = ZoneId.systemDefault();

        long parameterDay = timeValues.getLeft().toLocalDate().atStartOfDay(zoneId).toEpochSecond();
        long logMessageDay = timeValues.getRight().toLocalDate().atStartOfDay(zoneId).toEpochSecond();

        return parameterDay == logMessageDay;
    }
}
