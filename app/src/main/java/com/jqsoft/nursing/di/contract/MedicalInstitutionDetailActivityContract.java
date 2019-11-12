package com.jqsoft.nursing.di.contract;

import com.jqsoft.nursing.bean.MedicalInstitutionDetailBean;
import com.jqsoft.nursing.bean.base.HttpResultBaseBean;

/**
 * Created by Administrator on 2017/5/21.
 */

public interface MedicalInstitutionDetailActivityContract {
    interface View{

        void onLoadInfoSuccess(HttpResultBaseBean<MedicalInstitutionDetailBean> bean);

        void onLoadInfoFailure(String message);
    }

    interface  presenter{

    }
}
