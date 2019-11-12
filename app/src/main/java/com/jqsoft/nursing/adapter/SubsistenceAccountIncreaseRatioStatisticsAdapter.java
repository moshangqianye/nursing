package com.jqsoft.nursing.adapter;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jqsoft.nursing.R;
import com.jqsoft.nursing.adapter.base.BaseQuickAdapterEx;
import com.jqsoft.nursing.base.Constants;
import com.jqsoft.nursing.bean.grassroots_civil_administration.SubsistenceAccountIncreaseRatioNaturalBean;

import java.util.List;

/**
 * 资金台账 救助资金总支出增长率统计 适配器
 * Created by Administrator on 2018-01-04.
 */

public class SubsistenceAccountIncreaseRatioStatisticsAdapter extends BaseQuickAdapterEx<SubsistenceAccountIncreaseRatioNaturalBean, BaseViewHolder> {
    public SubsistenceAccountIncreaseRatioStatisticsAdapter(List<SubsistenceAccountIncreaseRatioNaturalBean> data) {
        super(R.layout.item_subsistence_account_increase_ratio_statistics_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SubsistenceAccountIncreaseRatioNaturalBean item) {
        int index = helper.getAdapterPosition();
        helper.setText(R.id.tv_month_name, item.getMonthName());
        helper.setText(R.id.tv_mom, item.getMom());
        helper.setText(R.id.tv_yoy, item.getYoy());
    }

    private void setEmptyString(BaseViewHolder helper){
        helper.setText(R.id.tv_month_name, Constants.EMPTY_STRING);
        helper.setText(R.id.tv_household_number, Constants.EMPTY_STRING);
        helper.setText(R.id.tv_person_number, Constants.EMPTY_STRING);
    }

}
