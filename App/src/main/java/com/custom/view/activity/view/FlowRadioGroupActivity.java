package com.custom.view.activity.view;

import android.os.Bundle;
import android.view.View;

import com.custom.view.R;
import com.custom.view.activity.base.BaseActivity;
import com.custom.view.view.FlowRadioGroup;
import com.custom.view.view.StarLevelView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class FlowRadioGroupActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flowradiogroup);

        setTitle(StarLevelView.class.getSimpleName());

        this.init();
    }

    private void init() {
        final FlowRadioGroup flowRadioGroup = (FlowRadioGroup) findViewById(R.id.view_flowRadioGroup);

        Map<String, String> items = new TreeMap<String, String>();
        items.put("A", "测试一");
        items.put("C", "测试二");
        items.put("B", "测试三");
        // 添加选项
        flowRadioGroup.setItems(items);

        // 设置选项监听
        flowRadioGroup.setOnCheckedItemChangeListener(new FlowRadioGroup.OnCheckedItemChangeListener() {

            @Override
            public void onCheckedChanged(View view, String chooseId, String chooseValue) {
                flowRadioGroup.setCompoundRightDrawables(flowRadioGroup.getCheckedRadioButtonId(),
                        R.mipmap.ic_launcher);
            }
        });
    }
}
