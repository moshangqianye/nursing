package com.jqsoft.nursing.di.contract;

import com.jqsoft.nursing.bean.PersonnelInfoData;
import com.jqsoft.nursing.bean.base.HttpResultBaseBean;
import com.jqsoft.nursing.bean.base.HttpResultEmptyBean;

/**
 * Created by Administrator on 2017/5/21.
 */

public interface AccessFileContract {
    interface View{

        void onLoadAccessFileSuccess(HttpResultBaseBean<PersonnelInfoData> bean);
        void onLoadAccessFileFailure(String message);

        void onLoadUpdatePeopleSuccess(HttpResultBaseBean<HttpResultEmptyBean> bean);
        void onLoadUpdatePeopleFailure(String message);

    }

    interface  presenter{

    }
}
