package com.jqsoft.nursing.adapter;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jqsoft.nursing.R;
import com.jqsoft.nursing.adapter.base.BaseQuickAdapterEx;
import com.jqsoft.nursing.base.Constants;
import com.jqsoft.nursing.bean.grassroots_civil_administration.MedicalAssistantFinanceAssuranceBean;

import java.util.List;

/**
 * 医疗救助-资助参保（参合）支出情况（一站式）适配器
 * Created by Administrator on 2018-01-04.
 */

public class MedicalAssistantFinanceAssuranceStatisticsAdapter extends BaseQuickAdapterEx<MedicalAssistantFinanceAssuranceBean, BaseViewHolder> {
    private String type;
    public MedicalAssistantFinanceAssuranceStatisticsAdapter(String type, List<MedicalAssistantFinanceAssuranceBean> data) {
        super(R.layout.item_medical_assistant_finance_assurance_5_column_statistics_layout, data);
        this.type = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, MedicalAssistantFinanceAssuranceBean item) {
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_first, item.getFirstValue());
        helper.setText(R.id.tv_second, item.getSecondValue());
        if (is5Column()){
            helper.setVisible(R.id.tv_third, true);
            helper.setVisible(R.id.tv_fourth, true);
            helper.setText(R.id.tv_third, item.getThirdValue());
            helper.setText(R.id.tv_fourth, item.getFourthValue());
        } else {
            helper.setVisible(R.id.tv_third, false);
            helper.setVisible(R.id.tv_fourth, false);
            helper.setText(R.id.tv_third, Constants.EMPTY_STRING);
            helper.setText(R.id.tv_fourth, Constants.EMPTY_STRING);
        }
    }

    private boolean is5Column(){
        if (Constants.MEDICAL_ASSISTANT_FINANCE_ASSURANCE_INCREASE_RATIO_VALUE.equals(type)){
            return true;
        } else {
            return false;
        }
    }


}
