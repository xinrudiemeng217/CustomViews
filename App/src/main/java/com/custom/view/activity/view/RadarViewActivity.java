package com.custom.view.activity.view;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.custom.view.R;
import com.custom.view.activity.base.BaseActivity;
import com.custom.view.view.FlowRadioGroup;
import com.custom.view.view.radarview.RadarData;
import com.custom.view.view.radarview.RadarView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

/**
 * @author https://github.com/qstumn/RadarChart
 */
public class RadarViewActivity extends BaseActivity {

    private RadarView mRadarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radarview);

        setTitle(RadarView.class.getSimpleName());

        this.init();
    }

    private void init() {
        mRadarView = (RadarView) findViewById(R.id.radarView);

        List<Integer> layerColor = new ArrayList<>();
        Collections.addAll(layerColor, 0x3300bcd4, 0x3303a9f4, 0x335677fc, 0x333f51b5, 0x33673ab7);
        mRadarView.setLayerColor(layerColor);
        List<String> vertexText = new ArrayList<>();
        Collections.addAll(vertexText, "力量", "敏捷", "速度", "智力", "精神", "耐力", "体力", "魔力", "意志", "幸运");
        mRadarView.setVertexText(vertexText);
        List<Float> values = new ArrayList<>();
        Collections.addAll(values, 3f, 6f, 2f, 7f, 5f, 1f, 4f, 3f, 8f, 5f);
        RadarData data = new RadarData(values);
        List<Float> values2 = new ArrayList<>();
        Collections.addAll(values2, 7f, 1f, 4f, 2f, 8f, 3f, 9f, 6f, 5f, 3f);
        RadarData data2 = new RadarData(values2);
        mRadarView.addData(data);
        mRadarView.addData(data2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.menu_radarview, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toggle_rotation:
                mRadarView.setRotationEnable(!mRadarView.isRotationEnable());
                break;
            case R.id.anime_value:
                mRadarView.animeValue(2000);
                break;
            case R.id.change_layer:
                int randomInt = new Random().nextInt(6) + 1;
                mRadarView.setLayer(randomInt);
                break;
            case R.id.change_web_mode:
                if (mRadarView.getWebMode() == RadarView.WEB_MODE_POLYGON) {
                    mRadarView.setWebMode(RadarView.WEB_MODE_CIRCLE);
                } else {
                    mRadarView.setWebMode(RadarView.WEB_MODE_POLYGON);
                }
                break;
            case R.id.toggle_line_enable:
                mRadarView.setRadarLineEnable(!mRadarView.isRadarLineEnable());
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
