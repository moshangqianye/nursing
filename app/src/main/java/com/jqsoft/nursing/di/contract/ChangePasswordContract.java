package com.jqsoft.nursing.di.contract;

import com.jqsoft.nursing.bean.grassroots_civil_administration.AboutInfoBean;
import com.jqsoft.nursing.bean.grassroots_civil_administration.base.GCAHttpResultBaseBean;


public interface ChangePasswordContract {
    interface View{

        void onLoadListSuccess(GCAHttpResultBaseBean<AboutInfoBean> bean);


        void onLoadListFailure(String message);


    }

    interface  presenter{

    }
}
