package com.jqsoft.nursing.adapter;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jqsoft.nursing.R;
import com.jqsoft.nursing.adapter.base.BaseQuickAdapterEx;
import com.jqsoft.nursing.bean.grassroots_civil_administration.MedicalAssistantMoneyConstitutionBean;

import java.util.List;

/**
 * 医疗救助资金构成
 医疗救助资金补偿方式构成
 医疗救助资金补偿类型构成  适配器
 * Created by Administrator on 2018-01-04.
 */

public class MedicalAssistantMoneyConstitutionStatisticsAdapter extends BaseQuickAdapterEx<MedicalAssistantMoneyConstitutionBean, BaseViewHolder> {
    public MedicalAssistantMoneyConstitutionStatisticsAdapter(List<MedicalAssistantMoneyConstitutionBean> data) {
        super(R.layout.item_medical_assistant_money_constitution_statistics_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MedicalAssistantMoneyConstitutionBean item) {
        helper.setText(R.id.tv_name, item.getName());
        helper.setText(R.id.tv_value, item.getValue());
    }


}
