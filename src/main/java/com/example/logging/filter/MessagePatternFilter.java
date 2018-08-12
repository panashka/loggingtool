package com.example.logging.filter;

import org.apache.commons.lang3.StringUtils;

/**
 * Custom message filter
 */
public class MessagePatternFilter extends Filter {
    public MessagePatternFilter(String parameter) {
        super(parameter);
    }

    @Override
    public boolean test(String line) {
        String customMessage = line.substring(StringUtils.ordinalIndexOf(line, " ", 2) + 1);
        return customMessage.contains(parameter);
    }
}
