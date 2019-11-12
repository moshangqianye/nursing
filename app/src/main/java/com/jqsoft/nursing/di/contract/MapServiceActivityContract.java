package com.jqsoft.nursing.di.contract;

import com.jqsoft.nursing.bean.grassroots_civil_administration.HeatmapBean;
import com.jqsoft.nursing.bean.grassroots_civil_administration.PersonLocationBean;
import com.jqsoft.nursing.bean.grassroots_civil_administration.base.GCAHttpResultBaseBean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/21.
 */

public interface MapServiceActivityContract {
    interface View{

        void onLoadListSuccess(GCAHttpResultBaseBean<List<PersonLocationBean>> bean);
        void onLoadMoreListSuccess(GCAHttpResultBaseBean<List<PersonLocationBean>> bean);

        void onLoadListFailure(String message, boolean isLoadMore);

        void onLoadHeatmapSuccess(GCAHttpResultBaseBean<HeatmapBean> bean);
        void onLoadHeatmapFailure(String msg);
    }

    interface  presenter{

    }
}
