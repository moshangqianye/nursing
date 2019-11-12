package com.jqsoft.nursing.adapter;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jqsoft.nursing.R;
import com.jqsoft.nursing.adapter.base.BaseQuickAdapterEx;
import com.jqsoft.nursing.base.Constants;
import com.jqsoft.nursing.bean.grassroots_civil_administration.SubsistenceArchiveTrendNaturalBean;

import java.util.List;

/**
 * 低保档案趋势分析适配器
 * Created by Administrator on 2018-01-04.
 */

public class SubsistenceArchiveTrendStatisticsAdapter extends BaseQuickAdapterEx<SubsistenceArchiveTrendNaturalBean, BaseViewHolder> {
    public SubsistenceArchiveTrendStatisticsAdapter(List<SubsistenceArchiveTrendNaturalBean> data) {
        super(R.layout.item_subsistence_archive_trend_statistics_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SubsistenceArchiveTrendNaturalBean item) {
        int index = helper.getAdapterPosition();
        helper.setText(R.id.tv_month_name, item.getMonthName());
        helper.setText(R.id.tv_household_number, item.getHouseholdNumber());
        helper.setText(R.id.tv_person_number, item.getPersonNumber());

    }

    private void setEmptyString(BaseViewHolder helper){
        helper.setText(R.id.tv_month_name, Constants.EMPTY_STRING);
        helper.setText(R.id.tv_household_number, Constants.EMPTY_STRING);
        helper.setText(R.id.tv_person_number, Constants.EMPTY_STRING);
    }

}
