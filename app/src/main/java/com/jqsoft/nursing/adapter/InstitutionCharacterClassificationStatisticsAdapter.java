package com.jqsoft.nursing.adapter;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jqsoft.nursing.R;
import com.jqsoft.nursing.adapter.base.BaseQuickAdapterEx;
import com.jqsoft.nursing.bean.grassroots_civil_administration.InstitutionCharacterNameValueBean;

import java.util.List;

/**
 * 机构性质分类统计适配器
 * Created by Administrator on 2018-01-04.
 */

public class InstitutionCharacterClassificationStatisticsAdapter extends BaseQuickAdapterEx<InstitutionCharacterNameValueBean, BaseViewHolder> {
    public InstitutionCharacterClassificationStatisticsAdapter(List<InstitutionCharacterNameValueBean> data) {
        super(R.layout.item_institution_character_classification_statistics_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, InstitutionCharacterNameValueBean item) {
        helper.setText(R.id.tv_institution_character, item.getName());
        helper.setText(R.id.tv_institution_number, item.getValue());
        helper.setText(R.id.tv_bed_number, item.getBednum());
        helper.setText(R.id.tv_percentage, item.getPercent());
    }


}
