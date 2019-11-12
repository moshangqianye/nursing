package com.jqsoft.nursing.di.contract;

import com.jqsoft.nursing.bean.grassroots_civil_administration.PersonCollectionBean;
import com.jqsoft.nursing.bean.grassroots_civil_administration.base.GCAHttpResultBaseBean;

import java.util.List;



public interface PersonCollectionActivityContract {
    interface View{

        void onLoadListSuccess(GCAHttpResultBaseBean<List<PersonCollectionBean>> bean);
        void onLoadMoreListSuccess(GCAHttpResultBaseBean<List<PersonCollectionBean>> bean);

        void onLoadListFailure(String message, boolean isLoadMore);


    }

    interface  presenter{

    }
}
