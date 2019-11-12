package com.jqsoft.nursing.di.contract;

import com.jqsoft.nursing.bean.ServicePackDetailBeanList;
import com.jqsoft.nursing.bean.base.HttpResultBaseBean;

import java.util.List;


/**
 * Created by Administrator on 2017/5/21.
 */

public interface ServicePackDetailContract {
    interface View{

        void onServicePackDetailSuccess(HttpResultBaseBean<List<ServicePackDetailBeanList>> bean);

        void onLoadMoreServicePackDetailSuccess(HttpResultBaseBean<List<ServicePackDetailBeanList>> bean);

        void onServicePackDetailFailure(String message);
    }

    interface  presenter{

    }
}
