package com.jqsoft.nursing.di.contract;

import com.jqsoft.nursing.bean.SocialDetailBean;
import com.jqsoft.nursing.bean.SubmitMapLocationResultBean;
import com.jqsoft.nursing.bean.grassroots_civil_administration.base.GCAHttpResultBaseBean;


public interface SocialDetailActivityContract {
    interface View{

        void onLoadListSuccess(GCAHttpResultBaseBean<SocialDetailBean> bean);
        void onLoadMoreListSuccess(GCAHttpResultBaseBean<SocialDetailBean> bean);
        void onLoadListFailure(String message, boolean isLoadMore);

        void onSubmitMapLocationSuccess(GCAHttpResultBaseBean<SubmitMapLocationResultBean> bean);
        void onSubmitMapLocationFailure(String message);
    }

    interface  presenter{

    }
}
