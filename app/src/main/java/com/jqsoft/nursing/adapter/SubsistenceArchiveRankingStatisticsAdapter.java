package com.jqsoft.nursing.adapter;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jqsoft.nursing.R;
import com.jqsoft.nursing.adapter.base.BaseQuickAdapterEx;
import com.jqsoft.nursing.bean.grassroots_civil_administration.SubsistenceArchiveRankingNaturalBean;

import java.util.List;

/**
 * 低保档案排名统计适配器
 * Created by Administrator on 2018-01-04.
 */

public class SubsistenceArchiveRankingStatisticsAdapter extends BaseQuickAdapterEx<SubsistenceArchiveRankingNaturalBean, BaseViewHolder> {
    public SubsistenceArchiveRankingStatisticsAdapter(List<SubsistenceArchiveRankingNaturalBean> data) {
        super(R.layout.item_subsistence_archive_ranking_statistics_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SubsistenceArchiveRankingNaturalBean item) {
        helper.setText(R.id.tv_district_name, item.getDistrictName());
        helper.setText(R.id.tv_household_number, item.getHouseholdNumber());
        helper.setText(R.id.tv_person_number, item.getPersonNumber());

    }


}
