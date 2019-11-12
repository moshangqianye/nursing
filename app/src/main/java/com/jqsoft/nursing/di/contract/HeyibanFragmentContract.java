package com.jqsoft.nursing.di.contract;

import com.jqsoft.nursing.bean.base.HttpResultBaseBean;
import com.jqsoft.nursing.bean.response.FriendResultWrapperBean;

/**
 * Created by Administrator on 2017/5/21.
 */

public interface HeyibanFragmentContract {
    interface View{

        void onLoadListSuccess(HttpResultBaseBean<FriendResultWrapperBean> bean);
        void onLoadMoreListSuccess(HttpResultBaseBean<FriendResultWrapperBean> bean);

        void onLoadListFailure(String message, boolean isLoadMore);
    }

    interface  presenter{

    }
}
