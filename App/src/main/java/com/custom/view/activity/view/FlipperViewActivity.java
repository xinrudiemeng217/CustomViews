package com.custom.view.activity.view;

import android.os.Bundle;
import android.view.View;

import com.custom.view.R;
import com.custom.view.activity.base.BaseActivity;
import com.custom.view.view.flipperview.FlipperView;

/**
 * @author https://github.com/JuniperPhoton/FlipperView
 */
public class FlipperViewActivity extends BaseActivity {

    private FlipperView mFlipperView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flipperview);

        setTitle(FlipperView.class.getSimpleName());

        this.init();
    }

    private void init() {
        mFlipperView = (FlipperView) findViewById(R.id.flipper_view);
        View prevView = findViewById(R.id.prev_btn);
        View nextView = findViewById(R.id.next_btn);

        prevView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFlipperView.previous();
            }
        });
        nextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFlipperView.next();
            }
        });
    }
}