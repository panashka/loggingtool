package com.example.logging.filter;

import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Created by ux501 on 11.08.2018.
 */
public class HourFilter extends TimePeriodFilter {
    public HourFilter(String parameter) {
        super(parameter);
    }

    @Override
    public boolean test(String line) {
        Pair<LocalDateTime, LocalDateTime> timeValues = getTimeValues(line);
        ZoneId zoneId = ZoneId.systemDefault();

        LocalDateTime parameterDate = timeValues.getLeft();
        LocalDateTime logMessageDate = timeValues.getRight();

        long parameterHour = parameterDate.toLocalDate().atTime(parameterDate.getHour(), 0).atZone(zoneId).toEpochSecond();
        long logMessageHour = logMessageDate.toLocalDate().atTime(logMessageDate.getHour(), 0).atZone(zoneId).toEpochSecond();

        return parameterHour == logMessageHour;
    }
}
