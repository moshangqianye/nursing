package com.jqsoft.nursing.di.contract;

import com.jqsoft.nursing.bean.HouseHoldeBackBean;
import com.jqsoft.nursing.bean.grassroots_civil_administration.base.GCAHttpResultBaseBean;

import java.util.List;


public interface AddServeryActivityContract {
    interface View{

        void onLoadListSuccess(GCAHttpResultBaseBean<List<HouseHoldeBackBean>> bean);
        void onLoadMoreListSuccess(GCAHttpResultBaseBean<List<HouseHoldeBackBean>> bean);
        void onLoadListFailure(String message, boolean isLoadMore);

    }

    interface  presenter{

    }
}
