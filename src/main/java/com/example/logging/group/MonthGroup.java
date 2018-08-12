package com.example.logging.group;

import java.nio.file.Path;
import java.time.ZoneId;
import java.util.Map;

/**
 * Month grouping
 */
public class MonthGroup extends TimePeriodGroup {
    @Override
    public Map<String, Long> group(Path path) {
        return groupByDate(path, this::getMonthOfEpoch);
    }

    private Long getMonthOfEpoch(String line) {
        ZoneId zoneId = ZoneId.systemDefault();
        return getDate(line).toLocalDate().withDayOfMonth(1).atTime(0,0).atZone(zoneId).toEpochSecond();
    }
}
