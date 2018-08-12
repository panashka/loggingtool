package com.example.logging.factories;

import com.example.logging.Argument;
import com.example.logging.group.*;

import static com.example.logging.Argument.valueOf;

/**
 * Group factory
 */
public class GroupFactory {
    public Group getGroup(String argument) {
        Group group;
        Argument arg = valueOf(argument.toUpperCase());
        switch(arg) {
            case USERNAME:
                group = new UsernameGroup();
                break;
            case DAY:
                group = new DayGroup();
                break;
            case HOUR:
                group = new HourGroup();
                break;
            case MONTH:
                group = new MonthGroup();
                break;
            default:
                group = null;
        }
        return group;
    }
}
