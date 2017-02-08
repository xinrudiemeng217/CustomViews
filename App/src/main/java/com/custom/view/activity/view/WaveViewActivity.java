package com.custom.view.activity.view;

import android.os.Bundle;
import android.widget.SeekBar;

import com.custom.view.R;
import com.custom.view.activity.base.BaseActivity;
import com.custom.view.view.PuzzleView;
import com.custom.view.view.WaveView;

public class WaveViewActivity extends BaseActivity {

    private SeekBar seekBar;
    private WaveView waveView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waveview);

        setTitle(WaveView.class.getSimpleName());

        this.init();
    }

    private void init() {
        seekBar = (SeekBar) findViewById(R.id.seek_bar);
        waveView = (WaveView) findViewById(R.id.wave_view);
        seekBar.setMax(100);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                waveView.setProgress(  progress /100f );
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
