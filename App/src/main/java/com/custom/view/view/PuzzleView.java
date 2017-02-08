package com.custom.view.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 拼图
 */
public class PuzzleView extends TableLayout {

	/**
	 * Context
	 */
	private Context context = null;

	// 游戏难易程度
	private int degree = PuzzleView.DEGREE_EASY;
	// 简单
	public final static int DEGREE_EASY = 1;
	// 正常
	public final static int DEGREE_NORMAL = 1;
	// 困难
	public final static int DEGREE_HARD = 1;

	// 行走步数
	private int step = 0;

	// 列数
	private int row = 3;
	// 行数
	private int col = 3;

	// 存放切割后的图片
	private Map<Point, ItemView> items = new HashMap<Point, ItemView>();

	/**
	 * 存放用户走过的路径
	 */
	private List<Integer> roadPath = new ArrayList<Integer>();

	// 上
	public final static int UP = -1;
	// 下
	public final static int DOWN = 1;
	// 左
	public final static int LEFT = -2;
	// 右
	public final static int RIGHT = 2;

	/**
	 * 图片数组,用于存放切割后的图片
	 */
	private ItemView[] itemViews;
	// 图片资源ID
	private int resId;

	// 空框坐标
	private int boxX = 0;
	// 空框坐标
	private int boxY = 0;

	/**
	 * 拼图图片
	 */
	private Bitmap bitmap = null;

	/** 是否正在进行拼图 */
	private boolean start = false;

	public boolean isStart() {
		return start;
	}

	public void setStart(boolean start) {
		this.start = start;
	}

	public PuzzleView(Context context) {
		super(context);
		this.context = context;
	}

	public PuzzleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	/**
	 * 初始化
	 */
	private void init() {
		this.removeAllViews();

		// 设置图片数组的大小
		itemViews = new ItemView[row * col];

		// 切割图片
		this.cutImage();

		// 重排列图片
		this.imageOrder(45);

		start = true;
	}

	/**
	 * 切割图片
	 */
	private void cutImage() {
		int width = getScreenWidth(context) * 2 / 3;
		int height = getScreenWidth(context) * 2 / 3;

		Bitmap bitmap = null;
		if (resId != 0) {
			bitmap = BitmapFactory.decodeResource(super.getResources(), resId);
		}
		else {
			bitmap = this.bitmap;
		}

		Matrix matrix = new Matrix();
		matrix.postScale((float) width / bitmap.getWidth(), (float) height / bitmap.getHeight());// 图片的宽高和屏幕的宽高比

		int x = 0;

		for (int i = 0; i < row; i++) {
			TableRow tableRow = new TableRow(context);
			for (int j = 0; j < col; j++) {
				// 将图片剪切为固定长宽小图片
				Bitmap itemBitmap = Bitmap.createBitmap(bitmap, bitmap.getWidth() / col * j, bitmap.getHeight() / row
						* i, bitmap.getWidth() / col, bitmap.getHeight() / row, matrix, true);

				itemViews[x] = new ItemView(context, i * 10 + j, itemBitmap, Color.WHITE, 1, 1, 1, 1, onClickListener);

				items.put(new Point(i, j), itemViews[x]);// 添加到map中,用于之后交换图片,可以根据Point获取imageview

				tableRow.addView(itemViews[x]);

				if (i == row - 1 && j == col - 1) {

					itemViews[x].setVisibility(View.INVISIBLE);// 预设最后一张图片为不可见

					itemViews[x].setId(100);// 预设最后一张图片id为100
				}

				x++;
			}

			this.addView(tableRow, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		}
	}

	/**
	 * 重排列图片
	 */
	private void imageOrder(int num) {
		int[] directions = { DOWN, UP, RIGHT, LEFT };

		int x = row - 1;// 定义x为右下角x坐标
		int y = col - 1;// 定义y为右下角y坐标

		for (int i = 0; i < num; i++) {
			// 随机生成一个方向
			int index = (int) (Math.random() * directions.length);

			int direction = directions[index];
			switch (direction) {
			case UP:
				if (x + 1 > row - 1) {
					break;
				}
				else {
					moveImageItem(items.get(new Point(x + 1, y)).getId(), false);
					x = x + 1;
				}
				break;
			case DOWN:
				if (x - 1 < 0) {
					break;
				}
				else {
					moveImageItem(items.get(new Point(x - 1, y)).getId(), false);
					x = x - 1;
				}
				break;
			case LEFT:
				if (y + 1 > col - 1) {
					break;
				}
				else {
					moveImageItem(items.get(new Point(x, y + 1)).getId(), false);
					y = y + 1;
				}
				break;
			case RIGHT:
				if (y - 1 < 0) {
					break;
				}
				else {
					moveImageItem(items.get(new Point(x, y - 1)).getId(), false);
					y = y - 1;
				}
				break;
			default:
				break;
			}
		}

		boxX = x;// 将x坐标赋给黑框x坐标
		boxY = y;// 将y坐标赋给黑框y坐标
	}

	/**
	 * Item点击事件
	 */
	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (onStatusListener != null) {
				if (onStatusListener.getTimerStatus()) {
					// 移动图片位置
					moveImageItem(v.getId(), true);
				}
				else {
					onStatusListener.message("请点击“开始”按钮");
				}
			}
			else {
				// 移动图片位置
				moveImageItem(v.getId(), true);
			}

			// 检查游戏是否结束
			checkComplete();
		}
	};

	/**
	 * 判断游戏是否结束,根据图片之前设置的index与id做比较
	 */
	private void checkComplete() {
		int k = 0;
		for (int i = 0; i < row * col; i++) {
			if (itemViews[i].getIndex() == itemViews[i].getId()) {
				k += 1;
			}
		}

		if (k == row * col - 1) {
			if (getOnStatusListener() != null) {
				start = false;
				getOnStatusListener().completed();
			}
		}
	}

	/**
	 * 拼图每个Item
	 */
	private class ItemView extends ImageView {

		// 定义的下标,用于最后判断拼图是否已经拼成
		int index;

		public ItemView(Context context, int index) {
			super(context);
			this.index = index;

		}

		public ItemView(Context context, int index, Bitmap bitmap, int color, int paddingLeft, int paddingTop,
				int paddingRight, int paddingBottom, OnClickListener onClickListener) {
			super(context);
			this.index = index;

			this.setId(index);
			this.setImageBitmap(bitmap);
			this.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
			this.setBackgroundColor(color);
			this.setOnClickListener(onClickListener);
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	/**
	 * 移动Item
	 * 
	 * @param b
	 */
	public void moveImageItem(int id, boolean step) {
		int x = id / 10;// 将传过来的i值转为x
		int y = id % 10;// 将传过来的i值转为y
		if (items.get(new Point(x - 1, y)) != null) {// 判断上面是否为空
			if (items.get(new Point(x - 1, y)).getId() == 100) {// 判断上面一张图片是否为黑框,是则交换位置
				itemViews[(x) * col + y].setVisibility(View.INVISIBLE);// 将当前一张图片设为不可见

				itemViews[(x - 1) * col + y].setImageDrawable(itemViews[(x) * col + y].getDrawable());// 将黑框图片设为与它交换位置的那张图

				itemViews[(x - 1) * col + y].setVisibility(View.VISIBLE);// 黑框设为可见

				items.get(new Point(x - 1, y)).setIndex(items.get(new Point(x, y)).getIndex());// 黑框获取当前图片的index

				items.get(new Point(x - 1, y)).setId((x - 1) * 10 + y);// 黑框的id跟着改变

				items.get(new Point(x, y)).setId(100);// 设置当前图片id为100

				roadPath.add(DOWN);// 将路径添加到listpath中

				boxX = boxX + 1;

				if (step) {
					// 步数发生变化
					changesOfStep();
				}
			}
		}
		if (items.get(new Point(x + 1, y)) != null) {// 判断下边是否为空
			if (items.get(new Point(x + 1, y)).getId() == 100) {// 判断下面一张图片是否为黑框,是则交换位置
				itemViews[(x) * col + y].setVisibility(View.INVISIBLE);
				itemViews[(x + 1) * col + y].setImageDrawable(itemViews[(x) * col + y].getDrawable());
				itemViews[(x + 1) * col + y].setVisibility(View.VISIBLE);
				items.get(new Point(x + 1, y)).setIndex(items.get(new Point(x, y)).getIndex());
				items.get(new Point(x + 1, y)).setId((x + 1) * 10 + y);
				items.get(new Point(x, y)).setId(100);

				roadPath.add(UP);

				boxX = boxX - 1;

				if (step) {
					// 步数发生变化
					changesOfStep();
				}
			}
		}
		if (items.get(new Point(x, y + 1)) != null) {// 判断右边是否为空
			if (items.get(new Point(x, y + 1)).getId() == 100) {// 判断右边一张图片是否为黑框,是则交换位置
				itemViews[(x) * col + y].setVisibility(View.INVISIBLE);
				itemViews[(x) * col + y + 1].setImageDrawable(itemViews[(x) * col + y].getDrawable());

				itemViews[(x) * col + y + 1].setVisibility(View.VISIBLE);

				items.get(new Point(x, y + 1)).setIndex(items.get(new Point(x, y)).getIndex());
				items.get(new Point(x, y + 1)).setId((x) * 10 + y + 1);

				items.get(new Point(x, y)).setId(100);

				roadPath.add(LEFT);

				boxY = boxY - 1;

				if (step) {
					// 步数发生变化
					changesOfStep();
				}
			}
		}
		if (items.get(new Point(x, y - 1)) != null) {// 判断左边是否为空
			if (items.get(new Point(x, y - 1)).getId() == 100) {// 判断左边一张图片是否为黑框,是则交换位置

				itemViews[(x) * col + y].setVisibility(View.INVISIBLE);
				itemViews[(x) * col + y - 1].setImageDrawable(itemViews[(x) * col + y].getDrawable());

				itemViews[(x) * col + y - 1].setVisibility(View.VISIBLE);

				items.get(new Point(x, y - 1)).setIndex(items.get(new Point(x, y)).getIndex());

				items.get(new Point(x, y - 1)).setId((x) * 10 + y - 1);

				items.get(new Point(x, y)).setId(100);

				roadPath.add(RIGHT);

				boxY = boxY + 1;

				if (step) {
					// 步数发生变化
					changesOfStep();
				}
			}
		}
	}

	/**
	 * 步数发生变化
	 */
	private void changesOfStep() {
		step += 1;
		if (getOnStatusListener() != null) {
			getOnStatusListener().step(step);
		}
	}

	/**
	 * 设置拼图图片
	 */
	public void setImageBitmap(Bitmap bitmap) {
		if (start) {
			if (getOnStatusListener() != null) {
				getOnStatusListener().status(start);
			}
		}
		else {
			if (bitmap != null) {
				if (getOnStatusListener() != null) {
					getOnStatusListener().start();
				}

				this.bitmap = bitmap;

				init();
			}
			else {

			}
		}
	}

	/**
	 * 设置拼图图片
	 */
	public void setImageResource(int resId) {
		if (start) {
			if (getOnStatusListener() != null) {
				getOnStatusListener().status(start);
			}
		}
		else {
			if (resId != 0) {
				if (getOnStatusListener() != null) {
					getOnStatusListener().start();
				}

				this.resId = resId;

				init();
			}
			else {

			}
		}
	}

	/**
	 * 拼图状态监听器
	 */
	private OnStatusListener onStatusListener = null;

	public OnStatusListener getOnStatusListener() {
		return onStatusListener;
	}

	public void setOnStatusListener(OnStatusListener onStatusListener) {
		this.onStatusListener = onStatusListener;
	}

	public interface OnStatusListener {

		void start();

		void step(int step);

		void completed();

		void status(boolean start);

		boolean getTimerStatus();

		void message(String message);
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
}