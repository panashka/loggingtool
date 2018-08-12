package com.example.logging.group;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

/**
 * Username grouping
 */
public class UsernameGroup extends Group {
    @Override
    public Map<String, Long> group(Path path) {
        Map<String, Long> result = new HashMap<>();
        try {
            result = Files.lines(path)
                    .map(line -> line.substring(0, line.indexOf(" ")))
                    .collect(groupingBy(identity(), counting()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
