package com.custom.view.activity.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.custom.view.R;
import com.custom.view.activity.base.BaseActivity;
import com.custom.view.view.StarLevelView;
import com.custom.view.view.TimerView;

import java.util.ArrayList;
import java.util.List;

public class TimerViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timerview);

        setTitle(StarLevelView.class.getSimpleName());

        this.init();
    }

    private void init() {
        final TimerView viewTimer1 = (TimerView)findViewById(R.id.view_timer1);
        viewTimer1.setOnTimeCompleteListener(new TimerView.OnTimeCompleteListener() {

            @Override
            public void complete() {
                viewTimer1.stop();
            }
        });

        final TimerView viewTimer2 = (TimerView)findViewById(R.id.view_timer2);
        viewTimer2.setOnTimeCompleteListener(new TimerView.OnTimeCompleteListener() {

            @Override
            public void complete() {

            }
        });

        Button btnStart = (Button)findViewById(R.id.btn_start);
        btnStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                viewTimer1.start();
                viewTimer2.setDirection(true).start();
            }
        });
    }
}
