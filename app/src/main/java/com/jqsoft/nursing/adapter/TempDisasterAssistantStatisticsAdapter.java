package com.jqsoft.nursing.adapter;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jqsoft.nursing.R;
import com.jqsoft.nursing.adapter.base.BaseQuickAdapterEx;
import com.jqsoft.nursing.base.Constants;
import com.jqsoft.nursing.bean.grassroots_civil_administration.TempDisasterAssistantBean;

import java.util.List;

/**
 * 临时（受灾）救助趋势分析，排名统计，救助水平（次均救助金额排名统计，次均救助金额趋势分析）适配器
 * Created by Administrator on 2018-01-04.
 */

public class TempDisasterAssistantStatisticsAdapter extends BaseQuickAdapterEx<TempDisasterAssistantBean, BaseViewHolder> {
    private String type;
    public TempDisasterAssistantStatisticsAdapter(String type, List<TempDisasterAssistantBean> data) {
        super(R.layout.item_temp_disaster_assistant_3_column_statistics_layout, data);
        this.type = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, TempDisasterAssistantBean item) {
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_first, item.getFirstValue());
        if (is3Column()){
            helper.setVisible(R.id.tv_second, true);
            helper.setText(R.id.tv_second, item.getSecondValue());
        } else {
            helper.setVisible(R.id.tv_second, false);
            helper.setText(R.id.tv_second, Constants.EMPTY_STRING);
        }
    }

    private boolean is3Column(){
        if (Constants.TEMP_DISASTER_ASSISTANT_TEMP_RANKING_TYPE.equals(type) ||
                Constants.TEMP_DISASTER_ASSISTANT_TEMP_TREND_TYPE.equals(type) ||
                Constants.TEMP_DISASTER_ASSISTANT_DISASTER_RANKING_TYPE.equals(type) ||
                Constants.TEMP_DISASTER_ASSISTANT_DISASTER_TREND_TYPE.equals(type)){
            return true;
        } else {
            return false;
        }
    }


}
