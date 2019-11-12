package com.jqsoft.nursing.di.contract;

import com.jqsoft.nursing.bean.base.HttpResultBaseBean;
import com.jqsoft.nursing.bean.base.HttpResultEmptyBean;


/**
 * Created by Administrator on 2017/5/21.
 */

public interface SaveExecuServeritemContract {
    interface View{

        void onSaveExecuServeritemSuccess(HttpResultBaseBean<HttpResultEmptyBean> bean);

        void onLoadSaveExecuServeritemFailure(String message);
    }

    interface  presenter{

    }
}
