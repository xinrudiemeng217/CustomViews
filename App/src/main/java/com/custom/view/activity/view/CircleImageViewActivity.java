package com.custom.view.activity.view;

import android.os.Bundle;

import com.custom.view.R;
import com.custom.view.activity.base.BaseActivity;
import com.custom.view.view.AutoHorizontalScrollTextView;
import com.custom.view.view.CircleImageView;

public class CircleImageViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circleimageview);

        setTitle(CircleImageView.class.getSimpleName());

        this.init();
    }

    private void init() {

    }
}
