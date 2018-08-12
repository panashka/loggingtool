package com.example.logging.factories;

import com.example.logging.Argument;
import com.example.logging.filter.*;

import static com.example.logging.Argument.*;

/**
 * Filter factory
 */
public class FilterFactory {
    public Filter getFilter(String argument, String value) {
        Filter filter;
        Argument arg = valueOf(argument.toUpperCase());
        switch(arg) {
            case USERNAME:
                filter = new UsernameFilter(value);
                break;
            case DAY:
                filter = new DayFilter(value);
                break;
            case HOUR:
                filter = new HourFilter(value);
                break;
            case MONTH:
                filter = new MonthFilter(value);
                break;
            case PATTERN:
                filter = new MessagePatternFilter(value);
                break;
            default:
                filter = null;
        }
        return filter;
    }


}
