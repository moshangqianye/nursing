package com.jqsoft.nursing.adapter;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jqsoft.nursing.R;
import com.jqsoft.nursing.adapter.base.BaseQuickAdapterEx;
import com.jqsoft.nursing.bean.grassroots_civil_administration.SubsistenceStandardAverageRankingNaturalBean;

import java.util.List;

/**
 * 平均补助水平排名统计适配器
 * Created by Administrator on 2018-01-04.
 */

public class SubsistenceStandardAverageRankingStatisticsAdapter extends BaseQuickAdapterEx<SubsistenceStandardAverageRankingNaturalBean, BaseViewHolder> {
    public SubsistenceStandardAverageRankingStatisticsAdapter(List<SubsistenceStandardAverageRankingNaturalBean> data) {
        super(R.layout.item_subsistence_standard_average_ranking_statistics_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SubsistenceStandardAverageRankingNaturalBean item) {
        helper.setText(R.id.tv_district_name, item.getDistrictName());
        helper.setText(R.id.tv_average_money_per_month_person, item.getAverageMoneyPerMonthPerson());
    }


}
