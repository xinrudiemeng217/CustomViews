package com.custom.view.activity.view;

import android.os.Bundle;

import com.custom.view.R;
import com.custom.view.activity.BaseActivity;
import com.custom.view.view.UnlockView;

public class UnlockViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unlockview);

        setTitle(UnlockView.class.getSimpleName());

        this.init();
    }

    private void init() {
        UnlockView unlockView = (UnlockView)findViewById(R.id.unlockView);

        unlockView.setUnlockCallBack(new UnlockView.UnlockCallBack() {

            @Override
            public void callback(UnlockView view, String value) {
                // 关闭软键盘
                view.closeKeyboard(getCurrentFocus());
            }
        });
    }
}
