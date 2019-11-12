package com.jqsoft.nursing.arcface;

import com.jqsoft.nursing.bean.base.HttpResultNurseBaseBean;
import com.jqsoft.nursing.bean.nursing.HealthListBean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/21.
 */

public interface HeadCollectActivityContract {
    interface View{

        /**
         * 成功回调方法
         * @param bean
         */
        void onLoadHealthListSuccess(HttpResultNurseBaseBean<List<HealthListBean>> bean);

        /**
         * 失败回调方法
         * @param message
         * @param isLoadMore
         */
        void onLoadHealthListFail(String message, boolean isLoadMore);
    }

    interface  presenter{

    }
}
