package com.jqsoft.nursing.adapter;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jqsoft.nursing.R;
import com.jqsoft.nursing.adapter.base.BaseQuickAdapterEx;
import com.jqsoft.nursing.bean.grassroots_civil_administration.InstitutionRankingNaturalBean;

import java.util.List;

/**
 * 供养机构排名统计适配器
 * Created by Administrator on 2018-01-04.
 */

public class InstitutionRankingStatisticsAdapter extends BaseQuickAdapterEx<InstitutionRankingNaturalBean, BaseViewHolder> {
    public InstitutionRankingStatisticsAdapter(List<InstitutionRankingNaturalBean> data) {
        super(R.layout.item_institution_ranking_statistics_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, InstitutionRankingNaturalBean item) {
        helper.setText(R.id.tv_district_name, item.getDistrictName());
//        helper.setText(R.id.tv_institution_number, item.getInstitutionNumber());
        helper.setText(R.id.tv_bed_number, item.getBedNumber());

    }


}
