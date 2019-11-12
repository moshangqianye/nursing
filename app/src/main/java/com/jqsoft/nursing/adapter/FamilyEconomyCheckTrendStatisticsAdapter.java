package com.jqsoft.nursing.adapter;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jqsoft.nursing.R;
import com.jqsoft.nursing.adapter.base.BaseQuickAdapterEx;
import com.jqsoft.nursing.base.Constants;
import com.jqsoft.nursing.bean.grassroots_civil_administration.FamilyEconomyCheckTrendBean;

import java.util.List;

/**
 * 家庭经济情况核对-趋势分析-适配器
 * Created by Administrator on 2018-01-04.
 */

public class FamilyEconomyCheckTrendStatisticsAdapter extends BaseQuickAdapterEx<FamilyEconomyCheckTrendBean, BaseViewHolder> {
    private String type;
    public FamilyEconomyCheckTrendStatisticsAdapter(String type, List<FamilyEconomyCheckTrendBean> data) {
        super(R.layout.item_family_economy_check_trend_3_column_statistics_layout, data);
        this.type = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, FamilyEconomyCheckTrendBean item) {
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_first, item.getFirstValue());
        int columnNumber = getColumnNumber();
        if (3 == columnNumber){
            helper.setVisible(R.id.tv_second, true);
            helper.setText(R.id.tv_second, item.getSecondValue());
        } else {
            helper.setVisible(R.id.tv_second, false);
            helper.setText(R.id.tv_second, Constants.EMPTY_STRING);
        }
    }

    private int getColumnNumber(){
        int result = 0;
        if (Constants.FAMILY_ECONOMY_CHECK_ACCEPTANCE_TREND.equals(type)){
            result = 3;
        } else if (Constants.FAMILY_ECONOMY_CHECK_REPORT_TREND.equals(type)){
            result = 2;
        } else if (Constants.FAMILY_ECONOMY_CHECK_REVIEW_TREND.equals(type)){
            result = 2;
        }
        return result;
    }


}
