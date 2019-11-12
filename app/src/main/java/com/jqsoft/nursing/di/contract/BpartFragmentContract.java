package com.jqsoft.nursing.di.contract;

import com.jqsoft.nursing.bean.PersonInfoList;
import com.jqsoft.nursing.bean.PersonnelInfoData;
import com.jqsoft.nursing.bean.base.HttpResultBaseBean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/21.
 */

public interface BpartFragmentContract {
    interface View{
        void onLoadListSuccess(HttpResultBaseBean<List<PersonInfoList>> bean);
        void onLoginFailure(String message);
        void onPersonnelInfo(HttpResultBaseBean<PersonnelInfoData> bean);
    }

    interface  presenter{

    }
}
