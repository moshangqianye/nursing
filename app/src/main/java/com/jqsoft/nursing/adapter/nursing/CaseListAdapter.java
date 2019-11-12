package com.jqsoft.nursing.adapter.nursing;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jqsoft.nursing.R;
import com.jqsoft.nursing.adapter.base.BaseQuickAdapterEx;
import com.jqsoft.nursing.bean.nursing.CaseListBean;

import java.util.List;

/**
 * @author yedong
 * @date 2019/1/17
 * 老人列表适配器
 */

public class CaseListAdapter extends BaseQuickAdapterEx<CaseListBean, BaseViewHolder> {

    public CaseListAdapter(List data) {
        super(R.layout.item_case_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final CaseListBean item) {

        String medicalTime = item.getMedicalTime();
        if (!TextUtils.isEmpty(medicalTime) && medicalTime.length() >= 10) {
            helper.setText(R.id.tv_date, "日期：" + medicalTime.substring(0, 10));
        } else {
            helper.setText(R.id.tv_date, "日期：");
        }
        helper.setText(R.id.tv_brief, "简述：" + item.getsMemo());

    }
}
