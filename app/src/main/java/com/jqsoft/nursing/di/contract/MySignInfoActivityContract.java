package com.jqsoft.nursing.di.contract;

import com.jqsoft.nursing.bean.base.HttpResultBaseBean;
import com.jqsoft.nursing.bean.response_new.SignInfoOverviewResultBean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/21.
 */

public interface MySignInfoActivityContract {
    interface View{

        void onLoadSignOverviewDataSuccess(HttpResultBaseBean<List<SignInfoOverviewResultBean>> bean);

        void onLoadSignOverviewDataFailure(String message);

    }

    interface  presenter{

    }
}
