package com.custom.view.activity.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;

import com.custom.view.R;
import com.custom.view.activity.base.BaseActivity;
import com.custom.view.view.autoscrollview.AutoHorizontalScrollTextView;
import com.custom.view.view.autoscrollview.AutoVerticalScrollTextView;

public class AutoScrollTextViewActivity extends BaseActivity {

    private String value = "跑马灯测试，跑马灯测试，跑马灯测试，跑马灯测试，跑马灯测试，跑马灯测试";

    private AutoVerticalScrollTextView autoVerticalScrollTextView;

    private String[] values = {"跑马灯测试，跑马灯测试，跑马灯测试，跑马灯测试，跑马灯测试，跑马灯测试"};

    private boolean isRunning = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autoscrolltextview);

        setTitle(AutoHorizontalScrollTextView.class.getSimpleName());

        this.init();
    }

    private void init() {
        AutoHorizontalScrollTextView autoHorizontalScrollTextView = (AutoHorizontalScrollTextView) findViewById(R.id.autoHorizontalScrollTextView);
        autoHorizontalScrollTextView.setText(value);

        autoVerticalScrollTextView = (AutoVerticalScrollTextView) findViewById(R.id.autoVerticalScrollTextView);
        autoVerticalScrollTextView.setText(values[0]);

        new Thread() {
            @Override
            public void run() {
                while (isRunning) {
                    SystemClock.sleep(3000);
                    handler.sendEmptyMessage(199);
                }
            }
        }.start();
    }

    private int number = 0;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            if (msg.what == 199) {
                autoVerticalScrollTextView.next();
                number++;
                autoVerticalScrollTextView.setText(values[number % values.length]);
            }

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRunning = false;
    }
}
