package com.jqsoft.nursing.adapter;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jqsoft.nursing.R;
import com.jqsoft.nursing.adapter.base.BaseQuickAdapterEx;
import com.jqsoft.nursing.base.Constants;
import com.jqsoft.nursing.bean.HospitalTypeBean;

import java.util.List;

/**
 * Created by Administrator on 2017-05-19.
 */

public class HospitalTypeFirstLevelAdapter extends BaseQuickAdapterEx<HospitalTypeBean, BaseViewHolder> {
    public HospitalTypeFirstLevelAdapter(List<HospitalTypeBean> data) {
        super(R.layout.item_hospital_type_first_level_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HospitalTypeBean item) {
        if (item!=null){
            String title = item.getFeatureTitle();
            helper.setImageResource(R.id.iv_icon, item.getFeatureIcon());
            helper.setText(R.id.tv_title, title);
            if (Constants.TITLE_ALL.equals(title)){
                helper.setVisible(R.id.iv_next_level, false);
            } else {
                helper.setVisible(R.id.iv_next_level, true);
            }
        }
    }
}
