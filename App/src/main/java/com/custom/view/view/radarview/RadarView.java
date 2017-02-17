package com.custom.view.view.radarview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.IntRange;
import android.support.v4.view.GestureDetectorCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import com.custom.view.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author changhai qiu
 *         Email:qstumn@163.com
 */
public class RadarView extends View {
    private Context mContext;
    private int mWidth;
    private int mHeight;

    private int mWebMode;
    public static final int WEB_MODE_POLYGON = 1;
    public static final int WEB_MODE_CIRCLE = 2;
    private double mPerimeter;

    private float mRadius;
    private PointF mPointCenter;
    private int mRadarLineColor;
    private float mRadarLineWidth;
    private boolean mRadarLineEnable;
    private int mLayer;
    private List<Integer> mLayerColor;
    private float mMaxValue;
    private List<String> mVertexText;
    private int mVertexTextColor;
    private float mVertexTextSize;
    private float mVertexTextOffset;
    private int mMaxVertex;

    private double mAngle;
    private double mRotateAngle;

    private List<RadarData> mRadarData;

    private Paint mRadarLinePaint;
    private Paint mLayerPaint;
    private TextPaint mVertexTextPaint;
    private Paint mValuePaint;
    private TextPaint mValueTextPaint;

    private GestureDetectorCompat mDetector;
    private Scroller mScroller;
    private float mFlingPoint;
    private double mRotateOrientation;
    private boolean mRotationEnable;

    private AnimeUtil mAnimeUtil;

    public RadarView(Context context) {
        this(context, null);
    }

    public RadarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initAttrs(attrs);
        init();
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.RadarView);
        mLayer = typedArray.getInt(R.styleable.RadarView_radar_layer, 5);
        mRotationEnable = typedArray.getBoolean(R.styleable.RadarView_rotation_enable, true);
        mWebMode = typedArray.getInt(R.styleable.RadarView_web_mode, WEB_MODE_POLYGON);
        mMaxValue = typedArray.getFloat(R.styleable.RadarView_max_value, 0);
        mRadarLineColor = typedArray.getColor(R.styleable.RadarView_radar_line_color, 0xFF9E9E9E);
        mRadarLineEnable = typedArray.getBoolean(R.styleable.RadarView_radar_line_enable, true);
        mRadarLineWidth = typedArray.getDimension(R.styleable.RadarView_radar_line_width, dp2px(1));
        mVertexTextColor = typedArray.getColor(R.styleable.RadarView_vertex_text_color, mRadarLineColor);
        mVertexTextSize = typedArray.getDimension(R.styleable.RadarView_vertex_text_size, dp2px(12));
        mVertexTextOffset = typedArray.getDimension(R.styleable.RadarView_vertex_text_offset, 0);
        typedArray.recycle();
    }

    private void init() {
        mAnimeUtil = new AnimeUtil(this);
        mScroller = new Scroller(mContext);
        mDetector = new GestureDetectorCompat(mContext, new GestureListener());
        mDetector.setIsLongpressEnabled(false);

        mRadarData = new ArrayList<>();
        mLayerColor = new ArrayList<>();
        initLayerColor();

        mRadarLinePaint = new Paint();
        mLayerPaint = new Paint();
        mValuePaint = new Paint();
        mVertexTextPaint = new TextPaint();
        mValueTextPaint = new TextPaint();
        initPaint();
    }

    private void initLayerColor() {
        if (mLayerColor == null) {
            mLayerColor = new ArrayList<>();
        }
        if (mLayerColor.size() < mLayer) {
            int size = mLayer - mLayerColor.size();
            for (int i = 0; i < size; i++) {
                mLayerColor.add(Color.TRANSPARENT);
            }
        }
    }

    private void initPaint() {
        mRadarLinePaint.setStyle(Paint.Style.STROKE);
        mRadarLinePaint.setStrokeWidth(mRadarLineWidth);
        mRadarLinePaint.setColor(mRadarLineColor);
        mRadarLinePaint.setAntiAlias(true);

        mLayerPaint.setStyle(Paint.Style.FILL);

        mVertexTextPaint.setColor(mVertexTextColor);
        mVertexTextPaint.setTextSize(mVertexTextSize);
        mVertexTextPaint.setAntiAlias(true);

        mValuePaint.setStrokeWidth(dp2px(1));
        mValueTextPaint.setFakeBoldText(true);
    }

    public int getWebMode() {
        return mWebMode;
    }

    public void setWebMode(@IntRange(from = WEB_MODE_POLYGON, to = WEB_MODE_CIRCLE) int mWebMode) {
        this.mWebMode = mWebMode;
        invalidate();
    }

    public int getRadarLineColor() {
        return mRadarLineColor;
    }

    public void setRadarLineColor(int radarLineColor) {
        this.mRadarLineColor = radarLineColor;
        mRadarLinePaint.setColor(mRadarLineColor);
        invalidate();
    }

    public float getRadarLineWidth() {
        return mRadarLineWidth;
    }

    public void setRadarLineWidth(float radarLineWidth) {
        this.mRadarLineWidth = radarLineWidth;
        mRadarLinePaint.setStrokeWidth(mRadarLineWidth);
        invalidate();
    }

    public boolean isRadarLineEnable() {
        return mRadarLineEnable;
    }

    public void setRadarLineEnable(boolean radarLineEnable) {
        this.mRadarLineEnable = radarLineEnable;
        invalidate();
    }

    public int getLayer() {
        return mLayer;
    }

    public void setLayer(int layer) {
        this.mLayer = layer;
        initLayerColor();
        invalidate();
    }

    public List<Integer> getLayerColor() {
        return mLayerColor;
    }

    public void setLayerColor(List<Integer> layerColor) {
        this.mLayerColor = layerColor;
        initLayerColor();
        invalidate();
    }

    public float getVertexTextOffset() {
        return mVertexTextOffset;
    }

    public void setVertexTextOffset(float vertexTextOffset) {
        this.mVertexTextOffset = vertexTextOffset;
        invalidate();
    }

    public float getMaxValue() {
        return mMaxValue;
    }

    public void setMaxValue(float maxValue) {
        this.mMaxValue = maxValue;
        invalidate();
    }

    public List<String> getVertexText() {
        return mVertexText;
    }

    public void setVertexText(List<String> vertexText) {
        this.mVertexText = vertexText;
        invalidate();
    }

    public int getVertexTextColor() {
        return mVertexTextColor;
    }

    public void setVertexTextColor(int vertexTextColor) {
        this.mVertexTextColor = vertexTextColor;
        mVertexTextPaint.setColor(mVertexTextColor);
        invalidate();
    }

    public float getVertexTextSize() {
        return mVertexTextSize;
    }

    public void setVertexTextSize(float vertexTextSize) {
        this.mVertexTextSize = vertexTextSize;
        mVertexTextPaint.setTextSize(mVertexTextSize);
        invalidate();
    }

    public boolean isRotationEnable() {
        return mRotationEnable;
    }

    public void setRotationEnable(boolean enable) {
        this.mRotationEnable = enable;
    }

    public void animeValue(int duration) {
        for (RadarData radarData : mRadarData) {
            animeValue(duration, radarData);
        }
    }

    public void animeValue(int duration, RadarData data) {
        if (!mAnimeUtil.isPlaying(data)) {
            mAnimeUtil.animeValue(AnimeUtil.AnimeType.ZOOM, duration, data);
        }
    }

    public void addData(RadarData data) {
        mRadarData.add(data);
        initData(data);
        animeValue(2000, data);
    }

    public void removeRadarData(RadarData data) {
        mRadarData.remove(data);
        invalidate();
    }

    private void initData(RadarData data) {
        List<Float> value = data.getValue();
        float max = Collections.max(value);
        float calc = max * 5 / 4;
        if (mMaxValue == 0 || mMaxValue < max) {
            mMaxValue = calc;
        }
        int valueSize = value.size();
        if (mMaxVertex < valueSize) {
            mMaxVertex = valueSize;
        }
        mAngle = 2 * Math.PI / mMaxVertex;
        if (mVertexText == null || mVertexText.size() == 0) {
            mVertexText = new ArrayList<>();
            for (int i = 0; i < valueSize; i++) {
                char text = (char) ('A' + i);
                mVertexText.add(String.valueOf(text));
            }
        } else if (mVertexText.size() < valueSize) {
            int size = valueSize - mVertexText.size();
            for (int i = 0; i < size; i++) {
                mVertexText.add("");
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mRadarData.size() == 0) {
            String hint = "no data";
            float hintWidth = mValueTextPaint.measureText(hint);
            canvas.drawText(hint, mPointCenter.x - hintWidth / 2, mPointCenter.y, mValueTextPaint);
        } else {
            calcRadius();
            drawRadar(canvas);
            drawData(canvas);
        }
    }

    private void drawRadar(Canvas canvas) {
        drawWeb(canvas);
        drawLines(canvas);
    }

    private void drawWeb(Canvas canvas) {
        if (mWebMode == WEB_MODE_POLYGON) {
            for (int i = mLayer; i >= 1; i--) {
                float radius = mRadius / mLayer * i;
                Path p = new Path();
                for (int j = 1; j <= mMaxVertex; j++) {
                    float x = (float) (mPointCenter.x + Math.sin(mAngle * j + mRotateAngle) * radius);
                    float y = (float) (mPointCenter.y + Math.cos(mAngle * j + mRotateAngle) * radius);
                    if (j == 1) {
                        p.moveTo(x, y);
                    } else {
                        p.lineTo(x, y);
                    }
                }
                p.close();
                mLayerPaint.setColor(mLayerColor.get(i - 1));
                canvas.drawPath(p, mLayerPaint);
                if (mRadarLineEnable) {
                    canvas.drawPath(p, mRadarLinePaint);
                }
            }
        } else if (mWebMode == WEB_MODE_CIRCLE) {
            for (int i = mLayer; i >= 1; i--) {
                float radius = mRadius / mLayer * i;
                mLayerPaint.setColor(mLayerColor.get(i - 1));
                canvas.drawCircle(mPointCenter.x, mPointCenter.y, radius, mLayerPaint);
                if (mRadarLineEnable) {
                    canvas.drawCircle(mPointCenter.x, mPointCenter.y, radius, mRadarLinePaint);
                }
            }
        }
    }

    private void drawLines(Canvas canvas) {
        drawVertex(canvas);
        if (!mRadarLineEnable) {
            return;
        }
        for (int i = 1; i <= mMaxVertex; i++) {
            Path p = new Path();
            p.moveTo(mPointCenter.x, mPointCenter.y);
            float x = (float) (mPointCenter.x + Math.sin(mAngle * i + mRotateAngle) * mRadius);
            float y = (float) (mPointCenter.y + Math.cos(mAngle * i + mRotateAngle) * mRadius);
            p.lineTo(x, y);
            canvas.drawPath(p, mRadarLinePaint);
        }
    }

    private void drawVertex(Canvas canvas) {
        for (int i = 1; i <= mMaxVertex; i++) {
            float x = (float) (mPointCenter.x + Math.sin(mAngle * i + mRotateAngle) * (mRadius + mVertexTextOffset));
            float y = (float) (mPointCenter.y + Math.cos(mAngle * i + mRotateAngle) * (mRadius + mVertexTextOffset));
            String text = mVertexText.get(i - 1);
            float textWidth = mVertexTextPaint.measureText(text);
            Paint.FontMetrics fontMetrics = mVertexTextPaint.getFontMetrics();
            float textHeight = fontMetrics.descent - fontMetrics.ascent;
            canvas.drawText(text, x - textWidth / 2, y + textHeight / 4, mVertexTextPaint);
        }
    }


    private void drawData(Canvas canvas) {
        for (int i = 0; i < mRadarData.size(); i++) {
            RadarData radarData = mRadarData.get(i);
            mValuePaint.setColor(radarData.getColor());
            mValueTextPaint.setTextSize(dp2px(radarData.getValueTextSize()));
            mValueTextPaint.setColor(radarData.getVauleTextColor());
            List<Float> values = radarData.getValue();
            Path p = new Path();
            PointF[] textPoint = new PointF[values.size()];
            for (int j = 1; j <= values.size(); j++) {
                float value = values.get(j - 1);
                double percent = value / mMaxValue;
                float x = (float) (mPointCenter.x + Math.sin(mAngle * j + mRotateAngle) * mRadius * percent);
                float y = (float) (mPointCenter.y + Math.cos(mAngle * j + mRotateAngle) * mRadius * percent);
                if (j == 1) {
                    p.moveTo(x, y);
                } else {
                    p.lineTo(x, y);
                }
                textPoint[j - 1] = new PointF(x, y);
            }
            p.close();
            mValuePaint.setAlpha(255);
            mValuePaint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(p, mValuePaint);
            mValuePaint.setStyle(Paint.Style.FILL);
            mValuePaint.setAlpha(150);
            canvas.drawPath(p, mValuePaint);
            if (radarData.isValueTextEnable()) {
                List<String> valueText = radarData.getValueText();
                for (int k = 0; k < textPoint.length; k++) {
                    String text = valueText.get(k);
                    float textWidth = mValueTextPaint.measureText(text);
                    Paint.FontMetrics fontMetrics = mValueTextPaint.getFontMetrics();
                    float textHeight = fontMetrics.descent - fontMetrics.ascent;
                    canvas.drawText(text, textPoint[k].x - textWidth / 2, textPoint[k].y + textHeight / 3, mValueTextPaint);
                }
            }
        }
    }


    private void calcRadius() {
        if (mVertexText == null || mVertexText.size() == 0) {
            mRadius = Math.min(mPointCenter.x, mPointCenter.y) - mVertexTextOffset;
        } else {
            String maxText = Collections.max(mVertexText, new Comparator<String>() {
                @Override
                public int compare(String lhs, String rhs) {
                    return lhs.length() - rhs.length();
                }
            });
            float maxTextWidth = mVertexTextPaint.measureText(maxText);
            if (mVertexTextOffset == 0) {
                Paint.FontMetrics fontMetrics = mVertexTextPaint.getFontMetrics();
                float textHeight = fontMetrics.descent - fontMetrics.ascent;
                mVertexTextOffset = (int) Math.sqrt(Math.pow(maxTextWidth, 2) + Math.pow(textHeight, 2)) / 2;
                if (mVertexTextOffset < dp2px(15)) {
                    mVertexTextOffset = dp2px(15);
                }
            }
            mRadius = Math.min(mPointCenter.x, mPointCenter.y) - (maxTextWidth + mVertexTextOffset);
            mPerimeter = 2 * Math.PI * mRadius;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!mRotationEnable) return super.onTouchEvent(event);
        return mDetector.onTouchEvent(event);
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            if (!mScroller.isFinished()) {
                mScroller.forceFinished(true);
            }
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (Math.abs(velocityX) > Math.abs(velocityY)) {
                mFlingPoint = e2.getX();
                mScroller.fling((int) e2.getX(), 0, (int) velocityX, 0, (int) (-mPerimeter + e2.getX()), (int) (mPerimeter + e2.getX()), 0, 0);
            } else if (Math.abs(velocityY) > Math.abs(velocityX)) {
                mFlingPoint = e2.getY();
                mScroller.fling(0, (int) e2.getY(), 0, (int) velocityY, 0, 0, (int) (-mPerimeter + e2.getY()), (int) (mPerimeter + e2.getY()));
            }
            invalidate();
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            double rotate = mRotateAngle;
            double dis = RotateUtil.getRotateAngle(new PointF(e2.getX() - distanceX, e2.getY() - distanceY)
                    , new PointF(e2.getX(), e2.getY()), mPointCenter);
            rotate += dis;
            handleRotate(rotate);
            mRotateOrientation = dis;
            return super.onScroll(e1, e2, distanceX, distanceY);
        }
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            int x = mScroller.getCurrX();
            int y = mScroller.getCurrY();
            int max = Math.max(Math.abs(x), Math.abs(y));
            double rotateDis = RotateUtil.CIRCLE_ANGLE * (Math.abs(max - mFlingPoint) / mPerimeter);
            double rotate = mRotateAngle;
            if (mRotateOrientation > 0) {
                rotate += rotateDis;
            } else if (mRotateOrientation < 0) {
                rotate -= rotateDis;
            }
            handleRotate(rotate);
            mFlingPoint = max;
            invalidate();
        }
    }

    private void handleRotate(double rotate) {
        rotate = RotateUtil.getNormalizedAngle(rotate);
        mRotateAngle = rotate;
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        mPointCenter = new PointF(w / 2, h / 2);
    }

    private float dp2px(float dpValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return dpValue * scale + 0.5f;
    }
}
