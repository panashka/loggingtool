package com.example.logging;

import com.example.logging.filter.Filter;
import com.example.logging.group.Group;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

/**
 * Receiver
 */
public class LogReader {

    public void filterLogFiles(List<Filter> filters, Path path) {
        Path currentDirectory = getCurrentDirectory();
        try {
            String result = getLogFileList(currentDirectory).stream()
                    .map(file -> filterLogFile(file, filters))
                    .collect(Collectors.joining("\n"));
            writeToFile(path, result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void groupLogFiles(Group group, Path path) {
        Path currentDirectory = getCurrentDirectory();
        try {
            Map<String, Long> map = getLogFileList(currentDirectory).stream()
                    .flatMap(file -> group.group(file).entrySet().stream())
                    .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, Long::sum));

            String result =  map.entrySet().stream()
                    .map(entry -> entry.getKey().concat(" : ").concat(String.valueOf(entry.getValue().longValue())))
                    .collect(Collectors.joining("\n"));

            writeToFile(path, result);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Path> getLogFileList(Path directory) throws IOException {
        return Files.list(directory)
                .filter(file -> Files.isRegularFile(file))
                .filter(file -> file.getFileName().toString().endsWith(".log"))
                .collect(Collectors.toList());
    }

    private String filterLogFile(Path file, List<Filter> filters) {
        String result = "";
        try {
            result = Files.lines(file)
                    .filter(getFilterFilePredicate(filters))
                    .collect(Collectors.joining("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private Predicate<String> getFilterFilePredicate(List<Filter> filters) {
        return line -> filters.stream()
                .allMatch(f -> f.test(line));
    }

    private Path getCurrentDirectory() {
        return Paths.get("");
    }

    private void writeToFile(Path path, String str) throws IOException {
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path)) {
            bufferedWriter.write(str);
        }
    }
}
