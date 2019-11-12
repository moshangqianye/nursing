package com.jqsoft.nursing.adapter;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jqsoft.nursing.R;
import com.jqsoft.nursing.adapter.base.BaseQuickAdapterEx;
import com.jqsoft.nursing.bean.grassroots_civil_administration.TempDisasterAssistancePercentageBean;

import java.util.List;

/**
 *
 * Created by Administrator on 2018-01-04.
 */

public class TempDisasterAssistancePercentageStatisticsAdapter extends BaseQuickAdapterEx<TempDisasterAssistancePercentageBean, BaseViewHolder> {
    public TempDisasterAssistancePercentageStatisticsAdapter(List<TempDisasterAssistancePercentageBean> data) {
        super(R.layout.item_temp_disaster_assistance_percentage_statistics_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TempDisasterAssistancePercentageBean item) {
        helper.setText(R.id.tv_name, item.getName());
        helper.setText(R.id.tv_value, item.getValue());
    }


}
