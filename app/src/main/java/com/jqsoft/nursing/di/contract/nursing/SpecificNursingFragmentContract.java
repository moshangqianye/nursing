package com.jqsoft.nursing.di.contract.nursing;

import com.jqsoft.nursing.bean.base.HttpResultNurseBaseBean;
import com.jqsoft.nursing.bean.nursing.NursingBean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/21.
 */

public interface SpecificNursingFragmentContract {
    interface View{

        void onLoadNursingListDataSuccess(HttpResultNurseBaseBean<List<NursingBean>> bean);
        void onLoadNursingListMoreDataSuccess(HttpResultNurseBaseBean<List<NursingBean>> bean);

        void onLoadNursingListDataFailure(String message, boolean isLoadMore);


    }

    interface  presenter{

    }
}
