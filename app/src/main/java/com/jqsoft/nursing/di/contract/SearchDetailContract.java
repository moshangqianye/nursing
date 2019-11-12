package com.jqsoft.nursing.di.contract;

import com.jqsoft.nursing.bean.InHospitalInspectBeanList;
import com.jqsoft.nursing.bean.base.HttpResultBaseBean;
import com.jqsoft.nursing.bean.base.HttpResultEmptyBean;

import java.util.List;


/**
 * Created by Administrator on 2017/5/21.
 */

public interface SearchDetailContract {
    interface View{

        void onSearchDetailSuccess(HttpResultBaseBean<List<InHospitalInspectBeanList>> bean);

        void onLoadMoreSearchDetailSuccess(HttpResultBaseBean<List<InHospitalInspectBeanList>> bean);

        void onSearchFailure(String message);


        void onPostImgSuccess(HttpResultBaseBean<HttpResultEmptyBean> bean);

        void onLoadPostImgFailure(String message);

    }

    interface  presenter{

    }
}
