package com.jqsoft.nursing.adapter;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jqsoft.nursing.R;
import com.jqsoft.nursing.adapter.base.BaseQuickAdapterEx;
import com.jqsoft.nursing.bean.grassroots_civil_administration.NameValueBean;

import java.util.List;

/**
 * 低保/特困人员供养/低收入家庭 年龄分类统计适配器
 * Created by Administrator on 2018-01-04.
 */

public class SubsistenceArchiveAgeClassificationStatisticsAdapter extends BaseQuickAdapterEx<NameValueBean, BaseViewHolder> {
    public SubsistenceArchiveAgeClassificationStatisticsAdapter(List<NameValueBean> data) {
        super(R.layout.item_subsistence_archive_age_classification_statistics_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NameValueBean item) {
        helper.setText(R.id.tv_age_range, item.getName());
        helper.setText(R.id.tv_person_number, item.getValue());
    }


}
