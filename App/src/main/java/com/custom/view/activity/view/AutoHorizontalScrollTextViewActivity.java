package com.custom.view.activity.view;

import android.os.Bundle;

import com.custom.view.R;
import com.custom.view.activity.base.BaseActivity;
import com.custom.view.view.AutoHorizontalScrollTextView;
import com.custom.view.view.UnlockView;

public class AutoHorizontalScrollTextViewActivity extends BaseActivity {

    private String value = "跑马灯测试，跑马灯测试，跑马灯测试，跑马灯测试，跑马灯测试，跑马灯测试";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autohorizontalscrolltextview);

        setTitle(AutoHorizontalScrollTextView.class.getSimpleName());

        this.init();
    }

    private void init() {
        AutoHorizontalScrollTextView autoHorizontalScrollTextView = (AutoHorizontalScrollTextView) findViewById(R.id.autoHorizontalScrollTextView);
        autoHorizontalScrollTextView.setText(value);
    }
}
