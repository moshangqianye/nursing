package com.jqsoft.nursing.di.contract;

import com.jqsoft.nursing.bean.PeopleSignInfoBean;
import com.jqsoft.nursing.bean.base.HttpResultBaseBean;

/**
 * Created by Administrator on 2017/5/21.
 */

public interface PeopleSignInfoFragmentContract {
    interface View{

        void onLoadListSuccess(HttpResultBaseBean<PeopleSignInfoBean> bean);
        void onLoadMoreListSuccess(HttpResultBaseBean<PeopleSignInfoBean> bean);

        void onLoadListFailure(String message, boolean isLoadMore);

    }

    interface  presenter{

    }
}
