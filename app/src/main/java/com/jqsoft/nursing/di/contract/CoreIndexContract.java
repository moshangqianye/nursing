package com.jqsoft.nursing.di.contract;

import com.jqsoft.nursing.bean.CoreIndexBeanList;
import com.jqsoft.nursing.bean.ModifyExecuedBean;
import com.jqsoft.nursing.bean.base.HttpResultBaseBean;
import com.jqsoft.nursing.bean.base.HttpResultEmptyBean;

import java.util.List;


/**
 * Created by Administrator on 2017/5/21.
 */

public interface CoreIndexContract {
    interface View{

        void onCoreIndexSuccess(HttpResultBaseBean<List<CoreIndexBeanList>> bean);

        void onLoadMoreCoreIndexSuccess(HttpResultBaseBean<List<CoreIndexBeanList>> bean);

        void onLoadCoreIndexFailure(String message);

        void onSaveExecuServeritemSuccess(HttpResultBaseBean<HttpResultEmptyBean> bean);

        void onLoadSaveExecuServeritemFailure(String message);


        void onModifyExecuServeritemSuccess(HttpResultBaseBean<ModifyExecuedBean> bean);

        void onLoadModifyExecuServeritemFailure(String message);

        void onDeleteExecuServeritemSuccess(HttpResultBaseBean<HttpResultEmptyBean> bean);

        void onLoadDeleteExecuServeritemFailure(String message);
    }

    interface  presenter{

    }
}
