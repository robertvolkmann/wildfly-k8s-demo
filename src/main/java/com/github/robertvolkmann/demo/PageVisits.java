package com.github.robertvolkmann.demo;

import java.io.Serializable;

public class PageVisits implements Serializable {

    private int pageViews = 0;

    public void increment() {
        pageViews++;
    }

    public int getValue() {
        return pageViews;
    }

}
