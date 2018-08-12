package com.example.logging.group;

import java.nio.file.Path;
import java.time.ZoneId;
import java.util.Map;

public class DayGroup extends TimePeriodGroup {
    @Override
    public Map<String, Long> group(Path path) {
        return groupByDate(path, this::getDayOfEpoch);
    }

    private Long getDayOfEpoch(String line) {
        ZoneId zoneId = ZoneId.systemDefault();
        return getDate(line).toLocalDate().atStartOfDay(zoneId).toEpochSecond();
    }
}
