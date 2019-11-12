package com.jqsoft.nursing.di.contract.nursing;

import com.jqsoft.nursing.bean.base.HttpResultEmptyBean;
import com.jqsoft.nursing.bean.base.HttpResultNurseBaseBean;
import com.jqsoft.nursing.bean.nursing.NursingTaskBean;
import com.jqsoft.nursing.bean.nursing.NursingTaskNewBean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/21.
 */

public interface NursingDetailActivityContract {
    interface View{

        void onLoadListSuccess(HttpResultNurseBaseBean<NursingTaskNewBean> bean);

        void onLoadListFailure(String message);

        void onEndNursingTaskSuccess(HttpResultNurseBaseBean<List<HttpResultEmptyBean>> bean);
        void onEndNursingTaskFailure(String msg);
    }

    interface  presenter{

    }
}
