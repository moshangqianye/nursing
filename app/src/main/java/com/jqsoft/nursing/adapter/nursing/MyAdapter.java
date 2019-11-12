package com.jqsoft.nursing.adapter.nursing;

/**
 * Created by YLL on 2018-4-24.
 */



import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jqsoft.nursing.R;
import com.jqsoft.nursing.bean.nursing.OrgnizationBean;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName ： MyAdapter
 * Created by： skxy on 2016/11/22.
 * DES ：适配器
 */
public class MyAdapter extends ArrayAdapter<OrgnizationBean> {
    private Context mContext;
    private List<OrgnizationBean> mStringArray;
    public MyAdapter(Context context, List<OrgnizationBean> list) {
        super(context, android.R.layout.simple_spinner_item, list);
        mContext = context;
        mStringArray=list;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        //修改Spinner展开后的字体颜色
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent,false);
        }

        //此处text1是Spinner默认的用来显示文字的TextView
        TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
        tv.setText(mStringArray.get(position).getsOrgName());
        tv.setTextSize(16f);
        tv.setTextColor(Color.BLACK);

        return convertView;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 修改Spinner选择后结果的字体颜色
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(android.R.layout.simple_spinner_item, parent, false);
        }

        //此处text1是Spinner默认的用来显示文字的TextView
        TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
        tv.setText(mStringArray.get(position).getsOrgName());
        tv.setTextSize(18f);
        tv.setTextColor(Color.WHITE);
        return convertView;
    }

}

