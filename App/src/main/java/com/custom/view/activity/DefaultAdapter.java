package com.custom.view.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.custom.view.R;

/**
 * DefaultAdapter
 */
public class DefaultAdapter extends BaseAdapter {

    // Context
    private Context context = null;
    // Values
    private CustomView[] values = null;

    public DefaultAdapter(Context context, CustomView[] values) {
        super();

        this.context = context;
        this.values = values;
    }

    @Override
    public int getCount() {
        return values.length;
    }

    @Override
    public Object getItem(int index) {
        return values[index];
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int index, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_view, null);

        TextView txvTitle = (TextView) convertView.findViewById(R.id.txv_title);
        TextView txvIntroduction = (TextView) convertView.findViewById(R.id.txv_introduction);

        CustomView cv = values[index];

        txvTitle.setText(cv.getTitle());
        txvIntroduction.setText(cv.getIntroduction());

        return convertView;
    }
}