package com.jqsoft.nursing.di.contract;

import com.jqsoft.nursing.bean.MyMessageBean;
import com.jqsoft.nursing.bean.grassroots_civil_administration.base.GCAHttpResultBaseBean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/21.
 */

public interface MyMessageActivityContract {
    interface View{

        void onLoadListSuccess(GCAHttpResultBaseBean<List<MyMessageBean>> bean);
        void onLoadMoreListSuccess(GCAHttpResultBaseBean<List<MyMessageBean>> bean);

        void onLoadListFailure(String message, boolean isLoadMore);


    }

    interface  presenter{

    }
}
