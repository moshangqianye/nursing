package com.jqsoft.nursing.di.contract;

import com.jqsoft.nursing.bean.PersonDoctorMessageInfo;
import com.jqsoft.nursing.bean.base.HttpResultBaseBean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/21.
 */

public interface OnLineChatintFragmentContract {
    interface View {

        void onLoadPolicyDataSuccess(HttpResultBaseBean<List<PersonDoctorMessageInfo>> bean);

        void onLoadRemindDataFailure(String message);

    }

    interface presenter {

    }
}
