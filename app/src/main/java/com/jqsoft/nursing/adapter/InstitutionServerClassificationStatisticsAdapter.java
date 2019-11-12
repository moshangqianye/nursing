package com.jqsoft.nursing.adapter;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jqsoft.nursing.R;
import com.jqsoft.nursing.adapter.base.BaseQuickAdapterEx;
import com.jqsoft.nursing.bean.grassroots_civil_administration.NameValuePercentBean;

import java.util.List;

/**
 * 管理服务人员分类统计适配器
 * Created by Administrator on 2018-01-04.
 */

public class InstitutionServerClassificationStatisticsAdapter extends BaseQuickAdapterEx<NameValuePercentBean, BaseViewHolder> {
    public InstitutionServerClassificationStatisticsAdapter(List<NameValuePercentBean> data) {
        super(R.layout.item_institution_server_classification_statistics_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NameValuePercentBean item) {
        helper.setText(R.id.tv_person_type, item.getName());
        helper.setText(R.id.tv_institution_number, item.getValue());
        helper.setText(R.id.tv_percentage, item.getPercent());
    }


}
