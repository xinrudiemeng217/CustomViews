package com.custom.view.activity.view;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.custom.view.R;
import com.custom.view.activity.base.BaseActivity;
import com.custom.view.view.PuzzleView;
import com.custom.view.view.UnlockView;

public class PuzzleViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzleview);

        setTitle(PuzzleView.class.getSimpleName());

        this.init();
    }

    private void init() {
        PuzzleView puzzleView = (PuzzleView) findViewById(R.id.puzzleView);

        puzzleView.setOnStatusListener(new PuzzleView.OnStatusListener() {

            @Override
            public void start() {
                ;
            }

            @Override
            public void step(int step) {
                ;
            }

            @Override
            public void completed() {
                Toast.makeText(PuzzleViewActivity.this, "拼图成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void status(boolean start) {
                if (start) {
                    Log.i("tag", "拼图已经开始了！");
                }
            }

            // 这里可以结合计时器判断是否点击开始拼图
            @Override
            public boolean getTimerStatus() {
                return true;
            }

            @Override
            public void message(String message) {
                Log.i("tag", message);
            }
        });

        puzzleView.setImageResource(R.drawable.puzzle);
    }
}
