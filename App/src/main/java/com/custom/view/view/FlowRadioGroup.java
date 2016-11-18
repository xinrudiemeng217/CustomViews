package com.custom.view.view;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * 动态RadioGroup
 */
public class FlowRadioGroup extends RadioGroup {

	/** Items */
	private Map<String, String> items = new HashMap<String, String>();
	/** Views */
	private Map<Integer, RadioButton> views = new HashMap<Integer, RadioButton>();

	/** 当前被选择的ID */
	private String chooseId;

	public String getChooseId() {
		return chooseId;
	}

	public void setChooseId(String chooseId) {
		this.chooseId = chooseId;
	}

	/** 当前被选择的值 */
	private String chooseValue;

	public String getChooseValue() {
		return chooseValue;
	}

	public void setChooseValue(String chooseValue) {
		this.chooseValue = chooseValue;
	}

	/** 当前被选择的控件ID */
	private int radioButtonId;

	public FlowRadioGroup(Context context) {
		super(context);
	}

	public FlowRadioGroup(Context context, AttributeSet attrs) {
		super(context, attrs);

		setOrientation(RadioGroup.VERTICAL);

		setGravity(Gravity.LEFT);

		setOnCheckedChangeListener(onCheckedChangeListener);
	}

	/**
	 * 添加Items
	 */
	public void setItems(Map<String, String> items) {
		this.clear();
		
		this.items = items;

		this.addAllRadioButton(items);
	}

	/**
	 * 清空所有Item
	 */
	public void clear() {
		this.items.clear();
		this.views.clear();

		removeAllViews();
	}

	/**
	 * 设置右边提示图像
	 */
	public void setCompoundRightDrawables(int key, int resId) {
		Drawable drawable = getResources().getDrawable(resId);
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());

		views.get(key).setCompoundDrawables(null, null, drawable, null);
	}

	/**
	 * 添加一个RadioButton
	 */
	private void addRadioButton(String key, String vlaue) {
		RadioButton radioButton = new RadioButton(getContext());

		int keyhashCode = valueTohashCode(key);
		radioButton.setId(keyhashCode);
		radioButton.setTag(key);
		radioButton.setText(key + "." + vlaue);

		views.put(keyhashCode, radioButton);

		addView(radioButton, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
	}

	private int valueTohashCode(String value) {
		byte bValue = 0;
		for (byte b : value.getBytes()) {
			bValue = b;
		}
		return bValue;
	}

	private OnCheckedChangeListener onCheckedChangeListener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			radioButtonId = group.getCheckedRadioButtonId();
			RadioButton radioButton = views.get(radioButtonId);

			if (radioButton != null) {
				chooseId = (String) radioButton.getTag();
				chooseValue = radioButton.getText().toString();

				if (onCheckedItemChangeListener != null) {
					onCheckedItemChangeListener.onCheckedChanged(radioButton, chooseId, chooseValue);
				}
			}
		}
	};

	public View getCheckedView() {
		return views.get(radioButtonId);
	}

	private void addAllRadioButton(Map<String, String> items) {
		this.sortMapByKey(items);

		for (Entry<String, String> entry : items.entrySet()) {
			this.addRadioButton(entry.getKey(), entry.getValue());
		}
		
		clearCheck();
	}

	/**
	 * 按Key进行排序，A、C、B → A、B、C
	 */
	private Map<String, String> sortMapByKey(Map<String, String> items) {
		Map<String, String> values = new TreeMap<String, String>(new Comparator<String>() {

			@Override
			public int compare(String obj1, String obj2) {
				// 降序排序
				return obj1.compareTo(obj2);
			}
		});

		values.putAll(items);
		return values;
	}

	public interface OnCheckedItemChangeListener {

		void onCheckedChanged(View view, String chooseId, String chooseValue);
	}

	private OnCheckedItemChangeListener onCheckedItemChangeListener = null;

	public OnCheckedItemChangeListener getOnCheckedItemChangeListener() {
		return onCheckedItemChangeListener;
	}

	public void setOnCheckedItemChangeListener(OnCheckedItemChangeListener onCheckedItemChangeListener) {
		this.onCheckedItemChangeListener = onCheckedItemChangeListener;
	}
}