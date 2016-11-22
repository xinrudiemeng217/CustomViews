package com.custom.view.view;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.IllegalFormatException;
import java.util.Locale;

import android.content.Context;
import android.os.SystemClock;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Chronometer;

/**
 * 支持正序倒序的计时器
 */
public class TimerView extends Chronometer {

	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss", Locale.getDefault());

	/** 计时总时间 */
	private long totalTime = 60;
	/** 剩余时间 */
	private long remainingTime = totalTime;

	/** 倒序、正序 */
	private boolean direction = false;

	public TimerView(Context context) {
		this(context, null, 0);
	}

	public TimerView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public TimerView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		setOnChronometerTickListener(onChronometerTickListener);
	}

	private OnChronometerTickListener onChronometerTickListener = new OnChronometerTickListener() {

		@Override
		public void onChronometerTick(Chronometer chronometer) {
			if (direction) {
				;
			}
			else {
				if (remainingTime != 0) {
					remainingTime--;
					updateTextValue(remainingTime);
				}
				else {

					if (onTimeCompleteListener != null) {
						onTimeCompleteListener.complete();
						setOnChronometerTickListener(null);
					}
				}
			}
		}
	};

	@Override
	public void start() {
		setBase(SystemClock.elapsedRealtime());// 复位计时器，停止计时
		super.start();
	}

	@Override
	public void stop() {
		super.stop();
		setBase(SystemClock.elapsedRealtime());// 复位计时器，停止计时
	}

	/**
	 * 更新计时器
	 */
	private synchronized void updateTextValue(long time) {
		setText(simpleDateFormat.format(new Date(time * 1000)));
	}

	/**
	 * 获取倒计是剩余时间
	 */
	public String getRemainingTime(SimpleDateFormat simpleDateFormat) {
		return simpleDateFormat.format(new Date((totalTime - remainingTime) * 1000));
	}

	public boolean isDirection() {
		return direction;
	}

	public TimerView setDirection(boolean direction) {
		this.direction = direction;
		return this;
	}

	public long getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(long totalTime) {
		this.totalTime = totalTime;
	}

	public interface OnTimeCompleteListener {

		/**
		 * 记时完成
		 */
		void complete();
	}

	private OnTimeCompleteListener onTimeCompleteListener = null;

	public OnTimeCompleteListener getOnTimeCompleteListener() {
		return onTimeCompleteListener;
	}

	public void setOnTimeCompleteListener(OnTimeCompleteListener onTimeCompleteListener) {
		this.onTimeCompleteListener = onTimeCompleteListener;
	}
}