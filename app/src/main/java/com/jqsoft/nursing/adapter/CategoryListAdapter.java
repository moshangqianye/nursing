package com.jqsoft.nursing.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.jqsoft.nursing.R;
import com.jqsoft.nursing.bean.BuildingRoomFloorBean;
import com.jqsoft.nursing.bean.DetaiFamilMember;

/**
 * Created by Administrator on 2018/3/10.
 */
public class CategoryListAdapter extends BaseAdapter {

    private ArrayList<BuildingRoomFloorBean> itemList = new ArrayList<>();
    private Context context;
    private LayoutInflater inflater;
    private int myposition;

    public CategoryListAdapter(Context context, ArrayList<BuildingRoomFloorBean> itemList,int myposition) {
        super();
        this.context = context;
        this.itemList = itemList;
        this.myposition=myposition;

    }

    @Override
    public int getCount() {

        return itemList.size();
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
            convertView = inflater.inflate(R.layout.category_item, null);
            viewHolder = new SocailDetailFaimilyMemAdapter.ViewHolder();

            viewHolder.basename = (TextView) convertView.findViewById(R.id.name);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (SocailDetailFaimilyMemAdapter.ViewHolder) convertView.getTag();
        }
        viewHolder.basename.setText(itemList.get(position).getBuilding().getBuildingNO());

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

    public void setSelectedPosition(int position) {
        myposition = position;
    }

}

