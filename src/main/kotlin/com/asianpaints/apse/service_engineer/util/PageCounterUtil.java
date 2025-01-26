package com.asianpaints.apse.service_engineer.util;

public class PageCounterUtil {
    //starting from 1 because first page is not having the page number
    private int counter = 1;

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
