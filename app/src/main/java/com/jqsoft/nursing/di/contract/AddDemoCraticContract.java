package com.jqsoft.nursing.di.contract;

import com.jqsoft.nursing.bean.DemoCraticBaseBean;
import com.jqsoft.nursing.bean.HttpResultTestBean;
import com.jqsoft.nursing.bean.Uploadpic;
import com.jqsoft.nursing.bean.VideoBackBean;
import com.jqsoft.nursing.bean.grassroots_civil_administration.base.GCAHttpResultBaseBean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/21.
 */

public interface AddDemoCraticContract {
    interface View{

        void onAddFindSuccess(HttpResultTestBean bean);
        void onAddFindFailure(String message);

        void onLoadListSuccess(GCAHttpResultBaseBean<VideoBackBean> bean);
        void onLoadMoreListSuccess(GCAHttpResultBaseBean<VideoBackBean> bean);
        void onLoadListFailure(String message, boolean isLoadMore);
        void onAddpicSuccess(GCAHttpResultBaseBean<List<Uploadpic>> bean);
        void onLoadMorepicListSuccess(GCAHttpResultBaseBean<List<Uploadpic>> bean);
        void onAddpicFailure(String message);
        void onLoadListSuccessx(GCAHttpResultBaseBean<DemoCraticBaseBean> bean);

        void onLoadMoreListSuccessx(GCAHttpResultBaseBean<DemoCraticBaseBean> bean);

        void onLoadListFailurex(String message, boolean isLoadMore);


    }

    interface  presenter{

    }
}
