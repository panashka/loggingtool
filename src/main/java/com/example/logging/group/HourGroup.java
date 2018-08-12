package com.example.logging.group;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;

public class HourGroup extends TimePeriodGroup {
    @Override
    public Map<String, Long> group(Path path) {
        return groupByDate(path, this::getHourOfEpoch);
    }

    private Long getHourOfEpoch(String line) {
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime dateTime = getDate(line);
        return dateTime.toLocalDate().atTime(dateTime.getHour(), 0).atZone(zoneId).toEpochSecond();
    }
}
