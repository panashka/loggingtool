package com.example.logging.group;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

abstract class TimePeriodGroup extends Group {

    Map<String, Long> groupByDate(Path path, Function<String, Long> function) {
        Map<String, Long> result = new HashMap<>();
        try {
            Map<Long, Long> map = Files.lines(path)
                    .collect(groupingBy(function, counting()));
            result = map.entrySet().stream()
                    .collect(toMap(entry -> {
                        Long millis = entry.getKey();
                        return getStringDateFromEpoch(millis);
                    }, Map.Entry::getValue));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    LocalDateTime getDate(String line) {
        int beginIndex = line.indexOf(' ');
        int endIndex = line.indexOf(' ', beginIndex + 1);
        String timePeriod = line.substring(beginIndex + 1, endIndex);

        return LocalDateTime.parse(timePeriod);
    }

    private String getStringDateFromEpoch(long epoch) {
        Instant instant = Instant.ofEpochMilli(epoch);
        return instant.atZone(ZoneId.systemDefault()).toLocalDateTime().toString();
    }
}
