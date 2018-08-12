package com.example.logging.filter;

/**
 * Created by ux501 on 11.08.2018.
 */
public abstract class Filter {
    protected String parameter;

    public Filter(String parameter) {
        this.parameter = parameter;
    }
    public abstract boolean test(String line);
}
