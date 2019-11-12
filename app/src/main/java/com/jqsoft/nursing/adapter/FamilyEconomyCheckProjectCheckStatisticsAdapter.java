package com.jqsoft.nursing.adapter;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jqsoft.nursing.R;
import com.jqsoft.nursing.adapter.base.BaseQuickAdapterEx;
import com.jqsoft.nursing.bean.grassroots_civil_administration.NameValueBean;

import java.util.List;

/**
 * 家庭经济情况核对-核对项目类统计-适配器
 * Created by Administrator on 2018-01-04.
 */

//public class FamilyEconomyCheckProjectCheckStatisticsAdapter extends BaseQuickAdapter<NameValueBean, BaseViewHolder> {
public class FamilyEconomyCheckProjectCheckStatisticsAdapter extends BaseQuickAdapterEx<NameValueBean, BaseViewHolder> {
    public FamilyEconomyCheckProjectCheckStatisticsAdapter(List<NameValueBean> data) {
        super(R.layout.item_family_economy_check_project_check_2_column_statistics_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NameValueBean item) {
        helper.setText(R.id.tv_name, item.getName());
        helper.setText(R.id.tv_value, item.getValue());
    }



}
