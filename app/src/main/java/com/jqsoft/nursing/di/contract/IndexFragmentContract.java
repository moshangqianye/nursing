package com.jqsoft.nursing.di.contract;

import com.jqsoft.nursing.bean.base.HttpResultNurseBaseBean;
import com.jqsoft.nursing.bean.grassroots_civil_administration.NotificationBean;
import com.jqsoft.nursing.bean.grassroots_civil_administration.PolicyBean;
import com.jqsoft.nursing.bean.grassroots_civil_administration.base.GCAHttpResultBaseBean;
import com.jqsoft.nursing.bean.nursing.NursingBean;

import java.util.List;

/**
 * Created by Administrator 2017/5/21.
 */

public interface IndexFragmentContract {
    interface View{

        void onLoadNotificationDataSuccess(GCAHttpResultBaseBean<List<NotificationBean>> bean);

        void onLoadNotificationDataFailure(String message);

        void onLoadPolicyDataSuccess(GCAHttpResultBaseBean<List<PolicyBean>> bean);

        void onLoadPolicyDataFailure(String message);

        void onLoadNursingListDataSuccess(HttpResultNurseBaseBean<List<NursingBean>> bean);
        void onLoadNursingListDataFailure(String message);
        void onLoadNursingListMoreDataSuccess(HttpResultNurseBaseBean<List<NursingBean>> bean);

    }

    interface  presenter{

    }
}
