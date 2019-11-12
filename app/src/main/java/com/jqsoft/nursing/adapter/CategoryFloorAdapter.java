package com.jqsoft.nursing.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jqsoft.nursing.R;
import com.jqsoft.nursing.bean.BuildingRoomFloorBean;
import com.jqsoft.nursing.bean.FloorList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/10.
 */
public class CategoryFloorAdapter extends BaseAdapter {

    private List<FloorList> floorLists = new ArrayList<>();
    private Context context;
    private LayoutInflater inflater;

    private int myposition;

    public CategoryFloorAdapter(Context context, List<FloorList> floorLists,int myposition) {
        super();
        this.context = context;
        this.floorLists = floorLists;
        this.myposition=myposition;

    }

    @Override
    public int getCount() {

        return floorLists.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        SocailDetailFaimilyMemAdapter.ViewHolder viewHolder = null;
        if (convertView == null) {
            inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.category_item_floor, null);
            viewHolder = new SocailDetailFaimilyMemAdapter.ViewHolder();

            viewHolder.basename = (TextView) convertView.findViewById(R.id.name);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (SocailDetailFaimilyMemAdapter.ViewHolder) convertView.getTag();
        }
        viewHolder.basename.setText(floorLists.get(position).getFloor().getFloorNO());
        if(position ==myposition){
            viewHolder.basename.setTextColor(Color.parseColor("#067eee"));
        }else {
            viewHolder.basename.setTextColor(Color.parseColor("#666666"));
        }


        return convertView;
    }

    static class ViewHolder {
        public TextView basename;


    }

}

