package com.example.logging.filter;

/**
 * Username test
 */
public class UsernameFilter extends Filter {
    public UsernameFilter(String parameter) {
        super(parameter);
    }

    @Override
    public boolean test(String line) {
        return line.startsWith(parameter);
    }
}
