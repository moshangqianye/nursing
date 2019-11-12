package com.jqsoft.nursing.di.ui.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jqsoft.nursing.R;
import com.jqsoft.nursing.bean.resident.SRCLoginAreaBean;
import com.jqsoft.nursing.callback.AddressCallBack;

import org.litepal.LitePal;

import java.util.List;

public class ProvinceFragment implements AdapterView.OnItemClickListener{
    private AddressCallBack callBack;
    private String code;
    private Context context;
    private AddressAdapter adapter;
    private ListView listview;
    private   List<SRCLoginAreaBean>  Province ;
    private String setName;

    public ProvinceFragment(Context context, AddressCallBack callBack){
        this.context = context;
        this.callBack = callBack;
        initView();
    }
    public void setCode(String code){
        this.code = code;
    }

    public ListView getListview() {
        return listview;
    }

    public View initView() {


//        List<SRCLoginAreaBean>   list = LitePal.where(" areaCode=? ",Identity.srcInfo.getAreaId() ).find(SRCLoginAreaBean.class);
//        if (list.get(0).getAreaLevel().equals("area_2")){
//            Province =LitePal.where(" areaPid=? ",Identity.srcInfo.getAreaId() ).find(SRCLoginAreaBean.class);
//
//
//        }else {
            Province = LitePal.where(" areaLevel=? ","area_2" ).find(SRCLoginAreaBean.class);


//        }





        listview = (ListView) LayoutInflater.from(context).inflate(R.layout.select_address_pop_listview,null);
        adapter = new AddressAdapter(Province);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(this);

        return listview;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


//        code = AddressManager.newInstance().getAllProvinces().get(i).getCode();
        if (callBack != null){
            callBack.selectProvince(Province.get(i));

            code=Province.get(i).getAreaCode();
//            callBack.selectProvince(AddressManager.newInstance().getAllProvinces().get(i));
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
            if (list.get(i).getAreaCode().equals(code)) {
                text.setTextColor(context.getResources().getColor(R.color.new_redb));
                ivSelect.setVisibility(View.VISIBLE);
            }
            return view;
        }
    }
}
