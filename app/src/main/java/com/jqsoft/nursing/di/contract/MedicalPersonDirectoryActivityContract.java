package com.jqsoft.nursing.di.contract;

import com.jqsoft.nursing.bean.base.HttpResultBaseBean;
import com.jqsoft.nursing.bean.response.MedicalPersonDirectoryResultBean;

import java.util.List;


public interface MedicalPersonDirectoryActivityContract {
    interface View{

        void onLoadListSuccess(HttpResultBaseBean<List<MedicalPersonDirectoryResultBean>> bean);
        void onLoadMoreListSuccess(HttpResultBaseBean<List<MedicalPersonDirectoryResultBean>> bean);

        void onLoadListFailure(String message, boolean isLoadMore);

    }

    interface  presenter{

    }
}
