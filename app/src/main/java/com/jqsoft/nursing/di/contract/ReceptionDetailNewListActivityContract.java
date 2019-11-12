package com.jqsoft.nursing.di.contract;

import com.jqsoft.nursing.bean.ReceptionDetailNewListBean;
import com.jqsoft.nursing.bean.grassroots_civil_administration.base.GCAHttpResultBaseBean;

/**
 * Created by Administrator on 2017/5/21.
 */

public interface ReceptionDetailNewListActivityContract {
    interface View{

        void onLoadListSuccess(GCAHttpResultBaseBean<ReceptionDetailNewListBean> bean);
        void onLoadListFailure(String message);


    }

    interface  presenter{

    }
}
