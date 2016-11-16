package com.custom.view.view;

import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * 四位数字密码解锁View
 */
public class UnlockView extends LinearLayout {

    /**
     * Views
     */
    private EditText[] items = null;

    public UnlockView(Context context) {
        this(context, null, 0);
    }

    public UnlockView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

        this.addView(4);
    }

    public UnlockView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 添加Item
     */
    @SuppressLint("NewApi")
    public void addView(int count) {
        items = new EditText[count];
        for (int i = 0; i < count; i++) {
            items[i] = new EditText(getContext());
            items[i].setId(i);
            items[i].setSingleLine();
            items[i].setInputType(InputType.TYPE_CLASS_NUMBER);
            items[i].setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
            // items[i].setCursorVisible(false);
            items[i].setTextColor(Color.BLACK);
            items[i].setGravity(Gravity.CENTER);
            items[i].setBackground(getBackgroundDrawable());
            items[i].setKeyListener(DigitsKeyListener.getInstance("0123456789"));

            // 大小、边距
            int screenWidth = getScreenWidth(getContext()) / 7;
            LayoutParams layoutParams = new LayoutParams(screenWidth, screenWidth);
            layoutParams.setMargins(20, 20, 20, 20);

            addView(items[i], layoutParams);
        }

        for (int i = 0; i < count; i++) {
            this.addTextChangedListener(items, i);
        }

        openKeyboard(items[0]);
    }

    /**
     * 设置Selector
     */
    public static StateListDrawable getBackgroundDrawable() {
        StateListDrawable stateListDrawable = new StateListDrawable();
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(Color.WHITE);// 填充
        gradientDrawable.setCornerRadius(16);// 圆角
        gradientDrawable.setStroke(1, Color.parseColor("#000000"));// 边框
        stateListDrawable.addState(new int[]{}, gradientDrawable);
        return stateListDrawable;
    }

    /**
     * 文本输入监听
     */
    private void addTextChangedListener(final EditText[] items, final int index) {
        items[index].addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 1) {
                    if (!(index == items.length - 1)) {
                        items[index + 1].setFocusable(true);
                        items[index + 1].requestFocus();
                    }
                }

                String textValue = "";
                if (items != null) {
                    boolean isComplete = false;
                    for (int i = 0; i < items.length; i++) {
                        if (TextUtils.isEmpty(items[i].getText().toString())) {
                            isComplete = true;
                        }

                        textValue += items[i].getText().toString();
                    }

                    if (!isComplete) {// 已输入完整密码
                        if (unlockCallBack != null) {
                            unlockCallBack.callback(UnlockView.this, textValue);
                        }
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                ;
            }

            @Override
            public void afterTextChanged(Editable s) {
                items[index].removeTextChangedListener(this);
                items[index].addTextChangedListener(this);
            }
        });

        items[index].setOnKeyListener(new OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (index > 0) {
                        items[index - 1].setFocusable(true);
                        items[index - 1].requestFocus();
                    }
                }
                return false;
            }
        });
    }

    /**
     * 清空文本内容
     */
    public void clear() {
        for (int i = 0; i < items.length; i++) {
            items[i].setText("");
        }

        items[0].setFocusable(true);
        items[0].requestFocus();
    }

    /**
     * 获取输入内容
     */
    public int getValue() {
        return 0;
    }

    /**
     * 屏幕的宽
     *
     * @param context Context
     * @return ScreenWidth
     */
    private int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    private UnlockCallBack unlockCallBack = null;

    public UnlockCallBack getUnlockCallBack() {
        return unlockCallBack;
    }

    public void setUnlockCallBack(UnlockCallBack unlockCallBack) {
        this.unlockCallBack = unlockCallBack;
    }

    public interface UnlockCallBack {

        /**
         * 输入完整4位密码回调此方法
         */
        void callback(UnlockView view, String value);
    }

    /**
     * 弹出软键盘
     *
     * @param editText EditText
     */
    private void openKeyboard(final EditText editText) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                InputMethodManager inputManager = (InputMethodManager) editText.getContext().getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(editText, 0);
            }

        }, 998);
    }

    /**
     * 关闭软键盘
     */
    public void closeKeyboard(View view) {
        if (view != null) {
            ((InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                    view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}