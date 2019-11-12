package com.jqsoft.nursing.adapter;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jqsoft.nursing.R;
import com.jqsoft.nursing.adapter.base.BaseQuickAdapterEx;
import com.jqsoft.nursing.base.Constants;
import com.jqsoft.nursing.bean.grassroots_civil_administration.FamilyEconomyCheckRankingBean;

import java.util.List;

/**
 * 家庭经济情况核对-排名统计-适配器
 * Created by Administrator on 2018-01-04.
 */

public class FamilyEconomyCheckRankingStatisticsAdapter extends BaseQuickAdapterEx<FamilyEconomyCheckRankingBean, BaseViewHolder> {
    private String type;
    public FamilyEconomyCheckRankingStatisticsAdapter(String type, List<FamilyEconomyCheckRankingBean> data) {
        super(R.layout.item_family_economy_check_ranking_3_column_statistics_layout, data);
        this.type = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, FamilyEconomyCheckRankingBean item) {
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
        if (Constants.FAMILY_ECONOMY_CHECK_ACCEPTANCE_RANKING.equals(type)){
            result = 3;
        } else if (Constants.FAMILY_ECONOMY_CHECK_REPORT_RANKING.equals(type)){
            result = 2;
        } else if (Constants.FAMILY_ECONOMY_CHECK_REVIEW_RANKING.equals(type)){
            result = 2;
        }
        return result;
    }


}
