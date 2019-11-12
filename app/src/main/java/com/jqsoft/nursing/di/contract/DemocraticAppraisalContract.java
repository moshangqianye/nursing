package com.jqsoft.nursing.di.contract;

import com.jqsoft.nursing.bean.DemocraticAppraisalBean;
import com.jqsoft.nursing.bean.grassroots_civil_administration.base.GCAHttpResultBaseBean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/22.
 */

public interface DemocraticAppraisalContract {
    interface View {

        void onLoadListSuccess(GCAHttpResultBaseBean<List<DemocraticAppraisalBean>> bean);

        void onLoadMoreListSuccess(GCAHttpResultBaseBean<List<DemocraticAppraisalBean>> bean);

        void onLoadListFailure(String message);


    }

    interface presenter {

    }
}
