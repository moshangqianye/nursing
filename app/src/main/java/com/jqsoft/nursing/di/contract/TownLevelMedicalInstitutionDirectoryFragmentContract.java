package com.jqsoft.nursing.di.contract;

import com.jqsoft.nursing.bean.TownLevelMedicalInstitutionBeanList;
import com.jqsoft.nursing.bean.base.HttpResultBaseBean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/21.
 */

public interface TownLevelMedicalInstitutionDirectoryFragmentContract {
    interface View{

        void onLoadListSuccess(HttpResultBaseBean<List<TownLevelMedicalInstitutionBeanList>> bean);
        void onLoadMoreListSuccess(HttpResultBaseBean<List<TownLevelMedicalInstitutionBeanList>> bean);

        void onLoadListFailure(String message, boolean isLoadMore);

    }

    interface  presenter{

    }
}
