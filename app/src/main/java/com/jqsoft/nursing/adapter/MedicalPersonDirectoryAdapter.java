package com.jqsoft.nursing.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jqsoft.nursing.R;
import com.jqsoft.nursing.adapter.base.BaseQuickAdapterEx;
import com.jqsoft.nursing.base.Constants;
import com.jqsoft.nursing.bean.response.MedicalPersonDirectoryResultBean;
import com.jqsoft.nursing.listener.NoDoubleClickListener;
import com.jqsoft.nursing.util.Util;
import com.jqsoft.nursing.utils2.AppUtils;
import com.jqsoft.nursing.utils3.util.StringUtils;

import java.util.List;



public class MedicalPersonDirectoryAdapter extends BaseQuickAdapterEx<MedicalPersonDirectoryResultBean, BaseViewHolder> {
//    private Context context;

    public MedicalPersonDirectoryAdapter(List<MedicalPersonDirectoryResultBean> data) {
        super(R.layout.item_medical_person_directory_layout, data);
//        this.context=context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final MedicalPersonDirectoryResultBean item) {
//        helper.setText(R.id.tv_person_name, Util.trimString(item.getDocName()+" "+item.getDocPhone()));
        helper.setText(R.id.tv_person_name, Util.trimString(item.getDocName()));
        helper.setText(R.id.tv_org_name,  Util.trimString(item.getOrgName()));
        helper.setText(R.id.tv_person_phone,  Util.trimString(item.getDocPhone()));
    //    helper.setText(R.id.tv_phone_number, Util.trimString(item.getPhoneNumber()));

        final String phoneNumber = Util.trimString(item.getDocPhone());
        final View dialView = helper.getView(R.id.iv_phone);
        dialView.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                super.onNoDoubleClick(v);
                if (!StringUtils.isBlank(phoneNumber)){
                    AppUtils.actionDial(dialView.getContext(),
                            phoneNumber);
                } else {
                    Util.showToast(dialView.getContext(),
                            Constants.HINT_PHONE_NUMBER_EMPTY);
                }

            }
        });
    }


}
