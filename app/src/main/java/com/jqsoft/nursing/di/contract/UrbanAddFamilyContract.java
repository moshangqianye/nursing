package com.jqsoft.nursing.di.contract;

import com.jqsoft.nursing.bean.base.HttpResultBaseBean;
import com.jqsoft.nursing.bean.base.HttpResultEmptyBean;
import com.jqsoft.nursing.bean.grassroots_civil_administration.UrbanLowFamilybianjiBean;

/**
 * Created by Administrator on 2017/5/21.
 */

public interface UrbanAddFamilyContract {
    interface View{

        void onUrbanBaseInfoSaveSuccess(HttpResultBaseBean<HttpResultEmptyBean> bean);
        void onUrbanBaseInfoSaveFailure(String message);

        void onUrbanBaseInfobianjiSuccess(HttpResultBaseBean<UrbanLowFamilybianjiBean> bean);
        void onUrbanBaseInfobianjiFailure(String message);


    }

    interface  presenter{

    }
}
