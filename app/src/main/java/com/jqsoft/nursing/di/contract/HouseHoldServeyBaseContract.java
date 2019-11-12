package com.jqsoft.nursing.di.contract;

import com.jqsoft.nursing.bean.HouseHoldBasebean;
import com.jqsoft.nursing.bean.grassroots_civil_administration.base.GCAHttpResultBaseBean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/21.
 */

public interface HouseHoldServeyBaseContract {
    interface View {

        void onLoadListSuccess(GCAHttpResultBaseBean<List<HouseHoldBasebean>> bean);

        void onLoadMoreListSuccess(GCAHttpResultBaseBean<List<HouseHoldBasebean>> bean);

        void onLoadListFailure(String message);


    }

    interface presenter {

    }
}
