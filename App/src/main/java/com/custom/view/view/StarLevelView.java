package com.custom.view.view;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.custom.view.R;

/**
 * 星形View
 */
public class StarLevelView extends View {

	/**
	 * Context
	 */
	private Context context = null;

	/** 状态资源图片ID（未开启、已开启、已完成） */
	private int[] status = { R.drawable.game_status_close, R.drawable.game_status_open, R.drawable.game_status_finish };

	/** 未开启 */
	private Bitmap bitmapClose = null;
	/** 已开启 */
	private Bitmap bitmapOpen = null;
	/** 已完成 */
	private Bitmap bitmapFinish = null;

	/** 未开启 */
	public final static int STATUS_CLOSE = 0;
	/** 已开启 */
	public final static int STATUS_OPEN = 1;
	/** 已完成 */
	public final static int STATUS_FINISH = 2;

	/** 顶点个数 */
	private int count;

	/** 角度 */
	private float angle;

	/** 最大半径 */
	private float radius;

	/** 中心坐标X */
	private int centerX;
	/** 中心坐标Y */
	private int centerY;

	// 画笔
	private Paint paint = new Paint();
	// 画笔
	private Paint textPaint = new Paint();
	// 画笔
	private TextPaint textCenterPaint = new TextPaint();

	/**
	 * 顶点集合
	 */
	private List<Point> lstPoints = new ArrayList<Point>();
	// 路径
	private Path path = new Path();

	/** 中间文字信息 */
	private String centerText = "中间提示文字信息";

	/** 记录当前点击Point */
	private Point point = null;

	public Point getPoint() {
		return point;
	}

	private Rect topLeftRect = null;
	private Rect topRightRect = null;
	private Rect lowerLeftRect = null;
	private Rect lowerRightRect = null;

	public StarLevelView(Context context) {
		super(context);
		this.context = context;
	}

	public StarLevelView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	public StarLevelView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
	}

	/**
	 * 初始化
	 */
	private void init() {
		// 顶点个数
		count = lstPoints.size();
		// 角度
		angle = (float) (Math.PI * 2 / count);

		bitmapClose = ((BitmapDrawable) getResources().getDrawable(status[0])).getBitmap();
		bitmapOpen = ((BitmapDrawable) getResources().getDrawable(status[1])).getBitmap();
		bitmapFinish = ((BitmapDrawable) getResources().getDrawable(status[2])).getBitmap();

		// 画笔参数
		paint.setColor(Color.parseColor("#674732"));
		paint.setStrokeWidth(1);
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.STROKE);

		textPaint.setColor(Color.parseColor("#674732"));
		textPaint.setAntiAlias(true);
		textPaint.setTextSize(getTextSize(getScreenWidth(context)));

		textCenterPaint.setColor(Color.parseColor("#674732"));
		textCenterPaint.setAntiAlias(true);
		textCenterPaint.setTextSize(getTextSize(getScreenWidth(context)) + 6);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		// 绘制多边形
		onDrawShape(canvas);
		// 绘制文字
		onDrawText(canvas);
		// 绘制中心文字
		onDrawCenterText(canvas);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// 最大半径
		radius = Math.min(h, w) / 2 * 0.65f;
		// 中心坐标
		centerX = w / 2;
		centerY = h / 2;

		int width = w;
		int height = h;

		topLeftRect = new Rect(centerX - width / 2, centerY - height / 2, centerX, centerY);
		topRightRect = new Rect(centerX, centerY - height / 2, centerX + width / 2, centerY);
		lowerLeftRect = new Rect(centerX - width / 2, centerY, centerX, centerY + height / 2);
		lowerRightRect = new Rect(centerX, centerY, centerX + width / 2, centerY + height / 2);

		postInvalidate();

		super.onSizeChanged(w, h, oldw, oldh);
	}

	/**
	 * 绘制多边形
	 * 
	 * @param canvas
	 */
	private void onDrawShape(Canvas canvas) {
		path.reset();
		for (int i = 0; i < count; i++) {
			float x;
			float y;
			// if (i == 0) {
			// x = centerX + radius;
			// y = centerY;
			// // path.moveTo(x, y);
			// }
			// else {
			// 根据半径，计算出蜘蛛丝上每个点的坐标
			x = (float) (centerX + radius * Math.cos(angle * (i + 4.5)));
			y = (float) (centerY + radius * Math.sin(angle * (i + 4.5)));
			// path.lineTo(x, y);
			// }

			setXY(lstPoints.get(i), x, y);
		}

		// 闭合路径
		// path.close();
		// 绘制路径
		// canvas.drawPath(path, paint);

		// 绘制线路
		// for (int i = 0; i < count; i++) {
		// for (int j = 0; j < count - 2; j++) {
		// canvas.drawLine(lstPoints.get(i).getX(), lstPoints.get(i)
		// .getY(), lstPoints.get(j).getX(), lstPoints.get(j)
		// .getY(), paint);
		// }
		// }

		for (int i = 0; i < count; i++) {
			int j = i + 2;
			if (j >= count) {
				j = j - count;
			}
			canvas.drawLine(lstPoints.get(i).getX(), lstPoints.get(i).getY(), lstPoints.get(j).getX(), lstPoints.get(j)
					.getY(), paint);
		}

		// 绘制关卡
		for (int i = 0; i < count; i++) {
			onDrawLevel(canvas, lstPoints.get(i));
		}
	}

	/**
	 * 绘制关卡
	 * 
	 * @param canvas
	 */
	private void onDrawLevel(final Canvas canvas, final Point point) {
		Bitmap bitmap = null;
		switch (point.getStatus()) {
		case STATUS_CLOSE://
			bitmap = bitmapClose;
			break;
		case STATUS_OPEN://
			bitmap = bitmapOpen;
			break;
		case STATUS_FINISH://
			bitmap = bitmapFinish;
			break;
		default:
			bitmap = bitmapClose;
			break;
		}

		if (onStatusLoadCallBack != null) {
			if (point.getStatus() == STATUS_FINISH) {
				onStatusLoadCallBack.load(canvas, point);
			}
			else {
				onDrawLevel(canvas, point, bitmap);
			}
		}
		else {
			onDrawLevel(canvas, point, bitmap);
		}
	}

	/**
	 * 绘制关卡
	 */
	public void onDrawLevel(Canvas canvas, Point point, Bitmap bitmap) {
		canvas.drawBitmap(bitmap, point.getX() - bitmap.getWidth() / 2, point.getY() - bitmap.getHeight() / 2, paint);
	}

	// /**
	// * 消息处理器
	// */
	// private Handler handler = new Handler(Looper.getMainLooper()) {
	//
	// @Override
	// public void handleMessage(Message msg) {
	// switch (msg.what) {
	// case STATUS_FINISH://
	// ;
	// break;
	// default:
	// break;
	// }
	// };
	// };
	//
	// private void sendMessage(Handler handler, int index, Object object, long
	// delayMillis) {
	// Message msg = new Message();
	// msg.what = index;
	// msg.obj = object;
	// handler.sendMessageDelayed(msg, delayMillis);
	// }

	/**
	 * 绘制文字
	 * 
	 * @param canvas
	 */
	private void onDrawText(Canvas canvas) {
		for (int i = 0; i < count; i++) {

			// 顶点坐标
			Point point = lstPoints.get(i);
			// X坐标
			float x = point.getX();
			// Y坐标
			float y = point.getY();
			// 标题长度
			float textLength = textPaint.measureText(point.getName());
			// Y坐标平移值
			float yTranslation = 25f;

			if (topLeftRect.contains((int) x, (int) y)) {
				canvas.drawText(point.getName(), x - textLength / 2, y - bitmapClose.getHeight() / 2 - yTranslation,
						textPaint);
			}
			else if (topRightRect.contains((int) x, (int) y)) {
				canvas.drawText(point.getName(), x - textLength / 2, y - bitmapClose.getHeight() / 2 - yTranslation,
						textPaint);
			}
			else if (lowerLeftRect.contains((int) x, (int) y)) {
				canvas.drawText(point.getName(), x - textLength / 2,
						y + bitmapClose.getHeight() / 2 + yTranslation * 2, textPaint);
			}
			else if (lowerRightRect.contains((int) x, (int) y)) {
				canvas.drawText(point.getName(), x - textLength / 2,
						y + bitmapClose.getHeight() / 2 + yTranslation * 2, textPaint);
			}
		}
	}

	/**
	 * 绘制中心文字
	 * 
	 * @param canvas
	 */
	private void onDrawCenterText(Canvas canvas) {
		if (!TextUtils.isEmpty(centerText)) {
			int layoutWidth = getCenterTextWidth(getScreenWidth(context));
			StaticLayout layout = new StaticLayout(centerText, textCenterPaint, layoutWidth, Alignment.ALIGN_CENTER,
					1.0F, 0.0F, true);
			canvas.save();
			canvas.translate(centerX - layoutWidth / 2, centerY - layout.getHeight() / 2);
			layout.draw(canvas);
			canvas.restore();
		}
	}

	/**
	 * 设置点的坐标
	 */
	private void setXY(Point point, float x, float y) {
		if (point != null) {
			point.setX(x);
			point.setY(y);
		}
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// 计算点击区域
			float x = event.getX();
			float y = event.getY();
			for (int i = 0; i < count; i++) {
				float x1 = lstPoints.get(i).getX();
				float y1 = lstPoints.get(i).getY();
				float x2 = x;
				float y2 = y;
				float distance = (float) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
				if (distance < bitmapClose.getWidth() / 2) {
					if (getOnClickPointListener() != null) {
						point = lstPoints.get(i);
						getOnClickPointListener().onClick(point);
					}
					break;
				}
			}
			break;
		case MotionEvent.ACTION_MOVE:
			break;
		case MotionEvent.ACTION_UP:
			break;
		default:
			break;
		}
		return super.onTouchEvent(event);
	}

	/**
	 * 点
	 */
	public static class Point extends BasePoint {

		// 状态
		int status;
		// URL
		String url;

		public Point(String id, String name, int status) {
			super(id, name);

			this.status = status;
		}

		public Point(String id, String name, int status, String url) {
			super(id, name);

			this.status = status;
			this.url = url;
		}

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}
	}

	/**
	 * 基点
	 */
	public static class BasePoint {

		// ID
		String id;
		// 名称
		String name;
		// 坐标X
		float x;
		// 坐标Y
		float y;

		public BasePoint() {

		}

		public BasePoint(String id, String name) {
			this.id = id;
			this.name = name;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public float getX() {
			return x;
		}

		public void setX(float x) {
			this.x = x;
		}

		public float getY() {
			return y;
		}

		public void setY(float y) {
			this.y = y;
		}
	}

	/**
	 * 根据分辨率求出字体大小
	 * 
	 * @param screenWidth
	 */
	private int getTextSize(int screenWidth) {
		int rate = (int) (10 * (float) screenWidth / 320);
		return rate < 16 ? 16 : rate;
	}

	/**
	 * 根据分辨率求出中心文字区域的宽
	 * 
	 * @param screenWidth
	 */
	private int getCenterTextWidth(int screenWidth) {
		return screenWidth / 4;
	}

	/**
	 * 屏幕的宽
	 * 
	 * @param context
	 *            Context
	 * @return ScreenWidth
	 */
	private int getScreenWidth(Context context) {
		return context.getResources().getDisplayMetrics().widthPixels;
	}

	/**
	 * 设置顶点信息
	 */
	public void setPoints(List<Point> points) {
		if (points == null) {
			return;
		}

		this.lstPoints.clear();

		this.lstPoints = points;
		// 初始化
		this.init();
		// 刷新
		this.invalidate();
	}

	/**
	 * 设置中心文字信息
	 */
	public void setCenterText(String centerText) {
		this.centerText = centerText;

		// 刷新
		this.invalidate();
	}

	/**
	 * 点击事件监听
	 */
	public interface OnClickPointListener {

		void onClick(Point point);
	}

	private OnClickPointListener onClickPointListener = null;

	public OnClickPointListener getOnClickPointListener() {
		return onClickPointListener;
	}

	public void setOnClickPointListener(OnClickPointListener onClickPointListener) {
		this.onClickPointListener = onClickPointListener;
	}

	/**
	 * 图片状态加载回调，完成状态时需要加载其他不同的图标，请实现此接口
	 */
	public interface OnStatusLoadCallBack {

		void load(Canvas canvas, Point point);
	}

	private OnStatusLoadCallBack onStatusLoadCallBack = null;

	public OnStatusLoadCallBack getOnStatusLoadCallBack() {
		return onStatusLoadCallBack;
	}

	public void setOnStatusLoadCallBack(OnStatusLoadCallBack onStatusLoadCallBack) {
		this.onStatusLoadCallBack = onStatusLoadCallBack;
	}
}