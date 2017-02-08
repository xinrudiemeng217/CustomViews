package com.custom.view.activity.view;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;

import com.custom.view.R;
import com.custom.view.activity.base.BaseActivity;
import com.custom.view.view.BadgeView;
import com.custom.view.view.WaveView;

public class BadgeViewActivity extends BaseActivity {

    private ImageView img1;
    private ImageView img2;
    private ImageView img3;
    private ImageView img4;
    private ImageView img5;
    private ImageView img6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_badgeview);

        setTitle(BadgeView.class.getSimpleName());

        this.init();
    }

    private void init() {
        img1 = (ImageView) findViewById(R.id.img_1);
        BadgeFactory.createDot(this).setBadgeCount(20).bind(img1);

        img2 = (ImageView) findViewById(R.id.img_2);
        BadgeFactory.createCircle(this).setBadgeCount(20).bind(img2);

        img3 = (ImageView) findViewById(R.id.img_3);
        BadgeFactory.createRectangle(this).setBadgeCount(20).bind(img3);

        img4 = (ImageView) findViewById(R.id.img_4);
        BadgeFactory.createOval(this).setBadgeCount(20).bind(img4);

        img5 = (ImageView) findViewById(R.id.img_5);
        BadgeFactory.createSquare(this).setBadgeCount(20).bind(img5);

        img6 = (ImageView) findViewById(R.id.img_6);
        BadgeFactory.createRoundRect(this).setBadgeCount(20).bind(img6);
    }

    static class BadgeFactory {
        public static BadgeView createDot(Context context) {
            return new BadgeView(context).setWidthAndHeight(10, 10).setTextSize(0).setBadgeGravity(Gravity.RIGHT | Gravity.TOP).setShape(BadgeView.SHAPE_CIRCLE).setSpace(4, 4);
        }

        public static BadgeView createCircle(Context context) {
            return new BadgeView(context).setWidthAndHeight(20, 20).setTextSize(12).setBadgeGravity(Gravity.RIGHT | Gravity.TOP).setShape(BadgeView.SHAPE_CIRCLE).setSpace(4, 4);
        }

        public static BadgeView createRectangle(Context context) {
            return new BadgeView(context).setWidthAndHeight(25, 20).setTextSize(12).setBadgeGravity(Gravity.RIGHT | Gravity.TOP).setShape(BadgeView.SHAPE_RECTANGLE).setSpace(4, 4);
        }

        public static BadgeView createOval(Context context) {
            return new BadgeView(context).setWidthAndHeight(25, 20).setTextSize(12).setBadgeGravity(Gravity.RIGHT | Gravity.TOP).setShape(BadgeView.SHAPE_OVAL).setSpace(4, 4);
        }

        public static BadgeView createSquare(Context context) {
            return new BadgeView(context).setWidthAndHeight(20, 20).setTextSize(12).setBadgeGravity(Gravity.RIGHT | Gravity.TOP).setShape(BadgeView.SHAPE_SQUARE).setSpace(4, 4);
        }

        public static BadgeView createRoundRect(Context context) {
            return new BadgeView(context).setWidthAndHeight(25, 20).setTextSize(12).setBadgeGravity(Gravity.RIGHT | Gravity.TOP).setShape(BadgeView.SHAPTE_ROUND_RECTANGLE).setSpace(4, 4);
        }
    }
}