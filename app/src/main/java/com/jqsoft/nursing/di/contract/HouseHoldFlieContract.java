package com.jqsoft.nursing.di.contract;

import com.jqsoft.nursing.bean.HouseFileBean;
import com.jqsoft.nursing.bean.grassroots_civil_administration.base.GCAHttpResultBaseBean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/22.
 */

public interface HouseHoldFlieContract {
    interface View {

        void onLoadListSuccess(GCAHttpResultBaseBean<List<HouseFileBean>> bean);

        void onLoadMoreListSuccess(GCAHttpResultBaseBean<List<HouseFileBean>> bean);

        void onLoadListFailure(String message);


    }

    interface presenter {

    }
}
