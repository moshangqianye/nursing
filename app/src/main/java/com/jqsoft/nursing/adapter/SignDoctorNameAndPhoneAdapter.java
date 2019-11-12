package com.jqsoft.nursing.adapter;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jqsoft.nursing.R;
import com.jqsoft.nursing.adapter.base.BaseQuickAdapterEx;
import com.jqsoft.nursing.bean.DoctorTeamInfo;
import com.jqsoft.nursing.util.Util;

import java.util.List;

/**
 * 签约医生名称与电话号码适配器
 */
public class SignDoctorNameAndPhoneAdapter extends BaseQuickAdapterEx<DoctorTeamInfo, BaseViewHolder> {
    public SignDoctorNameAndPhoneAdapter(List<DoctorTeamInfo> data) {
        super(R.layout.item_signed_doctor_name_and_phone_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DoctorTeamInfo item) {
        helper.setText(R.id.tv_name, Util.trimString(item.getDoctorName()));
        boolean isTeamLeader = item.isTeamLeader();
        if(isTeamLeader){
            helper.setVisible(R.id.iv_leader, true);
        } else {
            helper.setVisible(R.id.iv_leader, false);
        }
        helper.setText(R.id.tv_phone, Util.trimString(item.getDoctorPhone()));


    }
}
