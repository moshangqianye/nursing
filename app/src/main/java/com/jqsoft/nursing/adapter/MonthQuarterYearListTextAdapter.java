package com.jqsoft.nursing.adapter;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jqsoft.nursing.R;
import com.jqsoft.nursing.adapter.base.BaseQuickAdapterEx;
import com.jqsoft.nursing.feature.IDateRange;

import java.util.List;

/**
 * 月份,季度,年份列表
 * Created by Administrator on 2018-01-01.
 */

public class MonthQuarterYearListTextAdapter extends BaseQuickAdapterEx<IDateRange, BaseViewHolder> {
    public MonthQuarterYearListTextAdapter(List<IDateRange> list) {
        super(R.layout.item_month_quarter_year_text_layout, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, IDateRange item) {
        helper.setText(R.id.tv_text, item.getPresentation());
    }

}
