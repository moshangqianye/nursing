package com.jqsoft.nursing.di.contract;

import com.jqsoft.nursing.bean.base.HttpResultBaseBean;
import com.jqsoft.nursing.bean.response.VillageLevelMedicalInstitutionDirectoryResultBean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/21.
 */

public interface VillageLevelMedicalInstitutionDirectoryActivityContract {
    interface View{

        void onLoadListSuccess(HttpResultBaseBean<List<VillageLevelMedicalInstitutionDirectoryResultBean>> bean);
        void onLoadMoreListSuccess(HttpResultBaseBean<List<VillageLevelMedicalInstitutionDirectoryResultBean>> bean);

        void onLoadListFailure(String message, boolean isLoadMore);

    }

    interface  presenter{

    }
}
