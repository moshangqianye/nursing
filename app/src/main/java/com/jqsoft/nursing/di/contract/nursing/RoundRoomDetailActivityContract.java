package com.jqsoft.nursing.di.contract.nursing;

import com.jqsoft.nursing.bean.base.HttpResultEmptyBean;
import com.jqsoft.nursing.bean.base.HttpResultNurseBaseBean;
import com.jqsoft.nursing.bean.nursing.NursingTaskBean;
import com.jqsoft.nursing.bean.nursing.RoundRoomDetailBean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/21.
 */

public interface RoundRoomDetailActivityContract {
    interface View{

        void onLoadListSuccess(HttpResultNurseBaseBean<RoundRoomDetailBean> bean);

        void onLoadListFailure(String message);

        void onEndNursingTaskSuccess(HttpResultNurseBaseBean<List<HttpResultEmptyBean>> bean);
        void onEndNursingTaskFailure(String msg);


    }

    interface  presenter{

    }
}
