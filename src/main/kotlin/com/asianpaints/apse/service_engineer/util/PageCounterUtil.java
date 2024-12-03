package com.asianpaints.apse.service_engineer.util;

public class PageCounterUtil {
    private int counter = 0;
    private int totalPages = 0;

    public int increment() {
        return ++counter;
    }

    public void reset() {
        counter = 0;
    }

    public int totalPages() {
        return totalPages;
    }

    public void addToTotal(int add) {
        totalPages += add;
    }
}
