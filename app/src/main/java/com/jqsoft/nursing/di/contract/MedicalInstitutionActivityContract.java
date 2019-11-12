package com.jqsoft.nursing.di.contract;

import com.jqsoft.nursing.bean.MedicalInstitutionListBean;
import com.jqsoft.nursing.bean.base.HttpResultBaseBean;

/**
 * Created by Administrator on 2017/5/21.
 */

public interface MedicalInstitutionActivityContract {
    interface View{

        void onLoadListSuccess(HttpResultBaseBean<MedicalInstitutionListBean> bean);
        void onLoadMoreListSuccess(HttpResultBaseBean<MedicalInstitutionListBean> bean);

        void onLoadListFailure(String message);
    }

    interface  presenter{

    }
}
