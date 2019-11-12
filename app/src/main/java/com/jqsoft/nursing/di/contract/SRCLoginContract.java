package com.jqsoft.nursing.di.contract;

import com.jqsoft.nursing.bean.base.HttpResultBaseBean;
import com.jqsoft.nursing.bean.base.HttpResultNewBaseBean;
import com.jqsoft.nursing.bean.base.HttpResultNurseBaseBean;
import com.jqsoft.nursing.bean.grassroots_civil_administration.SRCLoginDataDictionaryBean;
import com.jqsoft.nursing.bean.grassroots_civil_administration.SRCLoginSalvationBean;
import com.jqsoft.nursing.bean.resident.SRCLoginAreaBean;
import com.jqsoft.nursing.bean.resident.SRCLoginBean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/21.
 */

public interface SRCLoginContract {
    interface View{

        void onLoginSuccess(HttpResultNewBaseBean<String> bean);

        void onLoginFailure(String message);

        void onLoginAreaSuccess(HttpResultBaseBean<List<SRCLoginAreaBean>> bean);
        void onLoginAreaFailure(String message);

        void onLoginDataDictionatySuccess(HttpResultBaseBean<List<SRCLoginDataDictionaryBean>> bean);
        void onLoginDataDictionatyFailure(String message);

        void onLoginSalvationSuccess(HttpResultBaseBean<List<SRCLoginSalvationBean>> bean);
        void onLoginSalvationFailure(String message);
    }

    interface  presenter{

    }
}
