package com.jqsoft.nursing.adapter;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jqsoft.nursing.R;
import com.jqsoft.nursing.adapter.base.BaseQuickAdapterEx;
import com.jqsoft.nursing.bean.grassroots_civil_administration.SubsistenceStandardRankingNaturalBean;

import java.util.List;

/**
 * 标准排名统计适配器
 * Created by Administrator on 2018-01-04.
 */

public class SubsistenceStandardRankingStatisticsAdapter extends BaseQuickAdapterEx<SubsistenceStandardRankingNaturalBean, BaseViewHolder> {
    public SubsistenceStandardRankingStatisticsAdapter(List<SubsistenceStandardRankingNaturalBean> data) {
        super(R.layout.item_subsistence_standard_ranking_statistics_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SubsistenceStandardRankingNaturalBean item) {
        helper.setText(R.id.tv_district_name, item.getDistrictName());
        helper.setText(R.id.tv_money_per_month_person, item.getMoneyPerMonthPerson());
    }


}
