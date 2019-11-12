package com.jqsoft.nursing.di.contract;

import com.jqsoft.nursing.bean.DoctorTeamInfo;
import com.jqsoft.nursing.bean.PeopleBaseInfoBean;
import com.jqsoft.nursing.bean.base.HttpResultBaseBean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/21.
 */

public interface PeopleBaseFragmentContract {
    interface View{

        void onLoadListSuccess(HttpResultBaseBean<PeopleBaseInfoBean> bean);
        void onLoadMoreListSuccess(HttpResultBaseBean<PeopleBaseInfoBean> bean);

        void onLoadListFailure(String message, boolean isLoadMore);


        void onLoadDoctorListSuccess(HttpResultBaseBean<List<DoctorTeamInfo>> bean);

        void onLoadDoctorListFailure(String message);

    }

    interface  presenter{

    }
}
