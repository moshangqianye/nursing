package com.jqsoft.nursing.di.contract;

import com.jqsoft.nursing.bean.MyMessageDetailBean;
import com.jqsoft.nursing.bean.grassroots_civil_administration.base.GCAHttpResultBaseBean;

/**
 * Created by Administrator on 2017/5/21.
 */

public interface MyMessageDetailActivityContract {
    interface View{

        void onLoadListSuccess(GCAHttpResultBaseBean<MyMessageDetailBean> bean);
        void onLoadMoreListSuccess(GCAHttpResultBaseBean<MyMessageDetailBean> bean);
        void onLoadListFailure(String message);


    }

    interface  presenter{

    }
}
