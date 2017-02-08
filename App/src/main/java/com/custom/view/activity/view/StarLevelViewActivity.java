package com.custom.view.activity.view;

import android.os.Bundle;

import com.custom.view.R;
import com.custom.view.activity.base.BaseActivity;
import com.custom.view.view.StarLevelView;

import java.util.ArrayList;
import java.util.List;

public class StarLevelViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starlevelview);

        setTitle(StarLevelView.class.getSimpleName());

        this.init();
    }

    private void init() {
        StarLevelView viewStarLevel = (StarLevelView) findViewById(R.id.view_star_level);

        List<StarLevelView.Point> points = new ArrayList<StarLevelView.Point>();
        for (int i = 0; i < 6; i++) {
            points.add(new StarLevelView.Point(i + "", "名称" + i, i % 3, null));
        }

        viewStarLevel.setPoints(points);

        viewStarLevel.setOnClickPointListener(new StarLevelView.OnClickPointListener() {

            @Override
            public void onClick(final StarLevelView.Point point) {
                switch (point.getStatus()) {
                    case StarLevelView.STATUS_CLOSE:

                        break;
                    case StarLevelView.STATUS_OPEN:

                        break;
                    case StarLevelView.STATUS_FINISH:// 已完成

                        break;
                    default:
                        break;
                }
            }
        });

//        viewStarLevel.setOnStatusLoadCallBack(new StarLevelView.OnStatusLoadCallBack() {
//
//            @Override
//            public void load(Canvas canvas, StarLevelView.Point point) {
//                ;
//            }
//        });
    }
}
