package com.example.logging.filter;

import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDateTime;

/**
 * Created by ux501 on 11.08.2018.
 */
public abstract class TimePeriodFilter extends Filter {
    public TimePeriodFilter(String parameter) {
        super(parameter);
    }

    protected Pair<LocalDateTime, LocalDateTime> getTimeValues(String line) {
        int beginIndex = line.indexOf(' ');
        int endIndex = line.indexOf(' ', beginIndex + 1);
        String timePeriod = line.substring(beginIndex + 1, endIndex);

        LocalDateTime parameterDay = LocalDateTime.parse(parameter);
        LocalDateTime logMessageDay = LocalDateTime.parse(timePeriod);

        return Pair.of(parameterDay, logMessageDay);
    }
}
