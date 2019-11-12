package com.jqsoft.nursing.di.contract;

import com.jqsoft.nursing.bean.DoctorTeamInfo;
import com.jqsoft.nursing.bean.InHospitalInspectBeanList;
import com.jqsoft.nursing.bean.PeopleBaseInfoBean;
import com.jqsoft.nursing.bean.base.HttpResultBaseBean;

import java.util.List;


public interface ModuleListFragmentContract {
    interface View {

        void onLoadPeopleSignInfoListSuccess(HttpResultBaseBean<List<InHospitalInspectBeanList>> bean);
        void onLoadPeopleSignInfoMoreListSuccess(HttpResultBaseBean<List<InHospitalInspectBeanList>> bean);

        void onLoadPeopleSignInfoListFailure(String message, boolean isLoadMore);


        void onLoadSignUserInfoSuccess(HttpResultBaseBean<PeopleBaseInfoBean> bean);
        void onLoadSignUserInfoMoreSuccess(HttpResultBaseBean<PeopleBaseInfoBean> bean);

        void onLoadSignUserInfoFailure(String message, boolean isLoadMore);


        void onLoadDoctorListSuccess(HttpResultBaseBean<List<DoctorTeamInfo>> bean);

        void onLoadDoctorListFailure(String message);
    }

    interface presenter {

    }
}
