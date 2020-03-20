package com.jqsoft.nursing.di.contract;

import com.jqsoft.nursing.bean.MedicalInstitutionListBean;
import com.jqsoft.nursing.bean.base.HttpResultBaseBean;
import com.jqsoft.nursing.bean.base.HttpResultNewBaseBean;
import com.jqsoft.nursing.bean.base.HttpResultNurseBaseBean;
import com.jqsoft.nursing.bean.nursing.HealthListBean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/21.
 */

public interface ArcFaceListActivityContract {
    interface View{

        /**
         * 成功回调方法
         * @param bean
         */
        void onLoadHealthListSuccess(HttpResultNewBaseBean<String> bean);

        void onLoadMoreListSuccess(HttpResultNewBaseBean<String> bean);

        /**
         * 失败回调方法
         * @param message
         * @param isLoadMore
         */
        void onLoadHealthListFail(String message, boolean isLoadMore);


        /**
         * 成功回调方法
         * @param bean
         */
        void onLoadHealthEndSuccess(HttpResultNewBaseBean<String> bean);

        /**
         * 失败回调方法
         * @param message
         */
        void onLoadHealtEndFail(String message);

    }

    interface  presenter{

    }
}
