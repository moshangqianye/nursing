package com.jqsoft.nursing.adapter;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jqsoft.nursing.R;
import com.jqsoft.nursing.adapter.base.BaseQuickAdapterEx;
import com.jqsoft.nursing.bean.grassroots_civil_administration.SubsistenceApprovePovertyReasonNaturalBean;

import java.util.List;

/**
 * 低保审批情况/特困人员供养/低收入家庭 致贫原因/退保原因适配器
 * Created by Administrator on 2018-01-04.
 */

public class SubsistenceApprovePovertyReasonStatisticsAdapter extends BaseQuickAdapterEx<SubsistenceApprovePovertyReasonNaturalBean, BaseViewHolder> {
    public SubsistenceApprovePovertyReasonStatisticsAdapter(List<SubsistenceApprovePovertyReasonNaturalBean> data) {
        super(R.layout.item_subsistence_approve_poverty_reason_statistics_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SubsistenceApprovePovertyReasonNaturalBean item) {
        helper.setText(R.id.tv_poverty_reason, item.getTitle());
        helper.setText(R.id.tv_household_number, item.getHouseholdNumber());
        helper.setText(R.id.tv_percentage, String.valueOf(item.getPercent()));
    }


}
