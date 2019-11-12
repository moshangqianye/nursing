package com.jqsoft.nursing.di.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jqsoft.nursing.R;
import com.jqsoft.nursing.base.Constants;
import com.jqsoft.nursing.bean.resident.SRCLoginAreaBean;
import com.jqsoft.nursing.callback.AddressCallBackTwo;
import com.jqsoft.nursing.di.ui.activity.ReceptionListActivity;
import com.jqsoft.nursing.util.StringUtils;
import com.jqsoft.nursing.util.Util;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;


public class DistrictFragmentTwo implements AdapterView.OnItemClickListener{
    private AddressCallBackTwo callBack;
    private String code ;
    private Context context;
    private AddressAdapter adapter;
    private  ListView listview;
    private String provinceCode ;
    private String cityCode;
    private List<SRCLoginAreaBean> districtsList = new ArrayList<SRCLoginAreaBean>();
    private List<SRCLoginAreaBean> alldistrictsList = LitePal.where(" areaLevel=? ","area_4" ).find(SRCLoginAreaBean.class);


    public DistrictFragmentTwo(Context context, AddressCallBackTwo callBack){
        this.context = context;
        this.callBack = callBack;
        initView();
    }
    public void setCode(String cityCode){
//        if ( !cityCode.equals(this.cityCode)){
//            this.code = null;
//        }
        if (StringUtils.isNoEmpty(code)){
            this.code = code;
        }
        this.cityCode = cityCode;
//        this.provinceCode = provinceCode;
//        districtsList.clear();
//        districtsList.addAll(AddressManager.newInstance().findProvinceByCode(provinceCode)
//                .findCityByCode(cityCode).getAllDistricts());
        for (int i=0;i<alldistrictsList.size();i++){
            if(alldistrictsList.get(i).getAreaPid().equals(cityCode)){
                districtsList.add(alldistrictsList.get(i));


            }
        }


        adapter.notifyDataSetChanged();
    }

    public ListView getListview() {
        return listview;
    }

    public View initView() {
        listview = (ListView) LayoutInflater.from(context).inflate(R.layout.select_address_pop_listview,null);
        //districtsList = AddressManager.newInstance().findProvinceByCode(provinceCode).findCityByCode(cityCode).getAllDistricts();
        adapter = new AddressAdapter(districtsList);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(this);
        return listview;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        code = districtsList.get(i).getAreaCode();
        if (callBack != null){
//            callBack.selectDistrict(districtsList.get(i));


            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.RECEPTION_ITEM_ACTIVITY_KEY,  districtsList.get(i));

            Util.gotoActivityWithBundle(context, ReceptionListActivity.class, bundle);

        }
        adapter.notifyDataSetChanged();
    }

    class AddressAdapter extends BaseAdapter {

        private List<SRCLoginAreaBean> list;

        public AddressAdapter(List<SRCLoginAreaBean> list){
            this.list = list;
        }

        @Override
        public int getCount() {
            if (list != null) {
                return list.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = LayoutInflater.from(context).inflate(R.layout.address_listiew_item_textview, null);
            TextView text = (TextView) view.findViewById(R.id.tvTextName);
            ImageView ivSelect = (ImageView) view.findViewById(R.id.ivSelect);
            text.setText(list.get(i).getAreaName());
            if (list.get(i).getAreaCode().equals(cityCode)) {
                text.setTextColor(context.getResources().getColor(R.color.new_redb));
                ivSelect.setVisibility(View.VISIBLE);
            }
            return view;
        }
    }
}
