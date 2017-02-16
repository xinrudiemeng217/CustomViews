package com.custom.view.activity.view;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.custom.view.R;
import com.custom.view.activity.base.BaseActivity;
import com.custom.view.view.AutoHorizontalScrollTextView;
import com.custom.view.view.CountTimeProgressView;
import com.kyleduo.switchbutton.SwitchButton;
import com.larswerkman.lobsterpicker.OnColorListener;
import com.larswerkman.lobsterpicker.sliders.LobsterShadeSlider;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

/**
 * @author https://github.com/sfyc23/CountTimeProgressView
 */
public class CountTimeProgressViewActivity extends BaseActivity {

    private CountTimeProgressView countTimeProgressView;
    private int checkedItem = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counttimeprogressview);

        setTitle(CountTimeProgressView.class.getSimpleName());

        this.init();
    }

    private void init() {
        countTimeProgressView = (CountTimeProgressView) findViewById(R.id.countTimeProgressView);
        findViewById(R.id.tv_countTime_style).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(CountTimeProgressViewActivity.this).setTitle("CountTime Style").setSingleChoiceItems(
                        new String[]{"JUMP", "SECOND", "CLOCK", "NONE"}, checkedItem,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                checkedItem = which;
                                switch (which) {
                                    case 0:
                                        countTimeProgressView.setTextStyle(CountTimeProgressView.TextStyle.JUMP);
                                        countTimeProgressView.setTitleCenter("跳过");
                                        countTimeProgressView.startCountTimeAnimation();
                                        dialog.dismiss();
                                        break;
                                    case 1:
                                        countTimeProgressView.setTextStyle(CountTimeProgressView.TextStyle.SECOND);
                                        countTimeProgressView.setTitleCenter("跳过（%s）s");
                                        countTimeProgressView.startCountTimeAnimation();
                                        dialog.dismiss();
                                        break;
                                    case 2:
                                        countTimeProgressView.setTextStyle(CountTimeProgressView.TextStyle.CLOCK);
                                        countTimeProgressView.startCountTimeAnimation();
                                        dialog.dismiss();
                                        break;
                                    case 3:
                                        countTimeProgressView.setTextStyle(CountTimeProgressView.TextStyle.NONE);
                                        countTimeProgressView.startCountTimeAnimation();
                                        dialog.dismiss();
                                        break;
                                    default:
                                        dialog.dismiss();
                                        break;
                                }
                            }
                        }).show();
            }
        });
        // start_angle
        ((DiscreteSeekBar) findViewById(R.id.seek_bar_start_angle)).setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {
                countTimeProgressView.setStartAngle(seekBar.getProgress());
                countTimeProgressView.startCountTimeAnimation();
            }
        });

        // seek_bar_countTime
        ((DiscreteSeekBar) findViewById(R.id.seek_bar_countTime)).setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
//                countTimeProgressView.setCountTime(value * 1000);
//                countTimeProgressView.startCountTimeAnimation();
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {
                countTimeProgressView.setCountTime(seekBar.getProgress() * 1000);
                countTimeProgressView.startCountTimeAnimation();
            }
        });
        // Border
        ((DiscreteSeekBar) findViewById(R.id.seekbar_border_width)).setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

                countTimeProgressView.setBorderWidth(seekBar.getProgress());
            }
        });

        // setBorderBottomColor
        ((LobsterShadeSlider) findViewById(R.id.shadeslider_bottom_color)).addOnColorListener(new OnColorListener() {
            @Override
            public void onColorChanged(@ColorInt int color) {
                countTimeProgressView.setBorderBottomColor(color);
            }

            @Override
            public void onColorSelected(@ColorInt int color) {
            }
        });

        //setBorderDrawColor
        ((LobsterShadeSlider) findViewById(R.id.shadeslider_draw_color)).addOnColorListener(new OnColorListener() {
            @Override
            public void onColorChanged(@ColorInt int color) {
                countTimeProgressView.setBorderDrawColor(color);
            }

            @Override
            public void onColorSelected(@ColorInt int color) {
            }
        });
        //setBorderDrawColor
        ((LobsterShadeSlider) findViewById(R.id.shadeslider_bg_color)).addOnColorListener(new OnColorListener() {
            @Override
            public void onColorChanged(@ColorInt int color) {
                countTimeProgressView.setBackgroundColor(color);
            }

            @Override
            public void onColorSelected(@ColorInt int color) {
            }
        });

        ((DiscreteSeekBar) findViewById(R.id.seekbar_ball_width)).setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {
                countTimeProgressView.setMarkBallWidth(seekBar.getProgress());
            }
        });
        // setBorderBottomColor
        ((LobsterShadeSlider) findViewById(R.id.shadeslider_ball_color)).addOnColorListener(new OnColorListener() {
            @Override
            public void onColorChanged(@ColorInt int color) {
                countTimeProgressView.setMarkBallColor(color);
            }

            @Override
            public void onColorSelected(@ColorInt int color) {
            }
        });

        SwitchButton sb = (SwitchButton) findViewById(R.id.sb_ball_flag);
        sb.setChecked(true);
        sb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                countTimeProgressView.setMarkBallFlag(isChecked);
            }
        });

        SwitchButton sb2 = (SwitchButton) findViewById(R.id.sb_clockwise);
        sb2.setChecked(true);
        sb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                countTimeProgressView.setClockwise(isChecked);
                countTimeProgressView.startCountTimeAnimation();
            }
        });

        countTimeProgressView.setStartAngle(0);
        countTimeProgressView.setCountTime(6000);
        countTimeProgressView.setTextStyle(CountTimeProgressView.TextStyle.SECOND);
        countTimeProgressView.setBorderWidth(8);
        countTimeProgressView.setBorderBottomColor(Color.GRAY);
        countTimeProgressView.setBorderDrawColor(Color.RED);
        countTimeProgressView.setBackgroundColor(Color.WHITE);
        countTimeProgressView.setMarkBallFlag(true);
        countTimeProgressView.setMarkBallWidth(12);
        countTimeProgressView.setMarkBallColor(Color.GREEN);
        countTimeProgressView.setTitleCenter("跳过（%s）s");

        countTimeProgressView.addOnEndListener(new CountTimeProgressView.OnEndListener() {
            @Override
            public void onAnimationEnd() {
                Toast.makeText(CountTimeProgressViewActivity.this, "时间到", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onClick(long overageTime) {
                if (countTimeProgressView.isRunning()) {
                    countTimeProgressView.cancelCountTimeAnimation();
                    Log.e("overageTime","overageTime = "+overageTime);
                } else {
                    countTimeProgressView.startCountTimeAnimation();
                }
            }
        });
        countTimeProgressView.startCountTimeAnimation();
    }
}
