package com.custom.view.activity;

import android.app.Activity;

/**
 * CustomView
 */
public class CustomView {

    // 标题
    private int title;
    // 介绍
    private int introduction;
    // Activity
    private Class<? extends Activity> viewClass;

    public CustomView(int title, int introduction, Class<? extends Activity> viewClass) {
        this.title = title;
        this.introduction = introduction;
        this.viewClass = viewClass;
    }

    public int getTitle() {
        return title;
    }

    public int getIntroduction() {
        return introduction;
    }

    public Class<? extends Activity> getViewClass() {
        return viewClass;
    }
}