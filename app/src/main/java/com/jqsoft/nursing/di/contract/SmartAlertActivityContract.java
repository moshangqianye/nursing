package com.jqsoft.nursing.di.contract;

import com.jqsoft.nursing.bean.base.HttpResultBaseBean;
import com.jqsoft.nursing.bean.resident.PolicyBean;
import com.jqsoft.nursing.bean.resident.RemindAndMessageBean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/21.
 */

public interface SmartAlertActivityContract {
    interface View{

        void onLoadPolicyDataSuccess(HttpResultBaseBean<List<PolicyBean>> bean);

        void onLoadPolicyDataFailure(String message);

        void onLoadRemindDataSuccess(HttpResultBaseBean<RemindAndMessageBean> bean);

        void onLoadRemindDataFailure(String message);

//        void onLoadIntelligentHonourAgreementOverviewDataSuccess(HttpResultBaseBean<IntelligentHonourAgreementOverviewResultBean> bean);
//
//        void onLoadIntelligentHonourAgreementOverviewDataFailure(String message);
    }

    interface  presenter{

    }
}
