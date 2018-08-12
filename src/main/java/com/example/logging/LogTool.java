package com.example.logging;

import com.example.logging.factories.FilterFactory;
import com.example.logging.factories.GroupFactory;
import com.example.logging.filter.Filter;
import com.example.logging.group.Group;
import org.apache.commons.cli.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

/**
 * Log tool main class
 */
public class LogTool {
    public static void main(String[] args) {

        Options options = getOptions(args);
        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = parseOptions(parser, options, args, formatter);

        String outputFile = cmd.getOptionValue("o");
        Path filePath = Paths.get(outputFile);

        LogReader reader = new LogReader();

        if (cmd.hasOption("f")) {
            handleFilterOption(cmd, reader, filePath);
        } else if (cmd.hasOption("g")) {
            handleGroupOption(cmd, reader, filePath);
        } else {
            printHelp(formatter, options);
        }
    }

    private static void handleGroupOption(CommandLine cmd, LogReader reader, Path filePath) {
        String value = cmd.getOptionValue("g");
        GroupFactory factory = new GroupFactory();
        Group group = factory.getGroup(value);
        reader.groupLogFiles(group, filePath);
    }

    private static void handleFilterOption(CommandLine cmd, LogReader reader, Path filePath) {
        Map<String, String> filterValueMap = Stream.of(cmd.getOptionValues("f"))
                .collect(toMap(k -> k.substring(0, k.indexOf("=")), v -> v.substring(v.indexOf("=") + 1)));

        FilterFactory factory = new FilterFactory();
        List<Filter> filters = filterValueMap.entrySet().stream()
                .map(entry -> factory.getFilter(entry.getKey(), entry.getValue()))
                .collect(toList());

        reader.filterLogFiles(filters, filePath);
    }

    private static CommandLine parseOptions(CommandLineParser parser, Options options, String[] args, HelpFormatter formatter) {
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            printHelp(formatter, options);
            System.exit(1);
        }
        return cmd;
    }

    private static Options getOptions(String[] args) {
        Options options = new Options();

        Option output = new Option("o", "output", true, "output file");
        output.setRequired(true);
        Option filter = Option.builder("f")
                .numberOfArgs(3)
                .optionalArg(true)
                .argName("property=value,property=value,...")
                .valueSeparator(',')
                .desc("filter log messages")
                .build();
        Option group = new Option("g", "group", true, "group logs by parameter");
        Option threadNumber = new Option("t", "threadNumber", true, "number of threads");

        options.addOption(group);
        options.addOption(output);
        options.addOption(filter);
        options.addOption(threadNumber);
        return options;
    }

    private static void printHelp(HelpFormatter formatter, Options options) {
        formatter.printHelp("logging-tool", options);
    }
}
