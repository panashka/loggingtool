package com.example.logging.filter;

/**
 * Abstract filter
 */
public abstract class Filter {
    protected String parameter;

    public Filter(String parameter) {
        this.parameter = parameter;
    }
    public abstract boolean test(String line);
}
