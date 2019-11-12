package com.jqsoft.nursing.di.contract;

import com.jqsoft.nursing.bean.SignSeverPakesBeanList;
import com.jqsoft.nursing.bean.base.HttpResultBaseBean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/21.
 */

public interface SignClientSeverPakesContract {
    interface View{

        void onLoadListSuccess(HttpResultBaseBean<List<SignSeverPakesBeanList>> bean);
        void onLoadMoreListSuccess(HttpResultBaseBean<List<SignSeverPakesBeanList>> bean);

        void onLoadListFailure(String message);
    }

    interface  presenter{

    }
}
