package com.jqsoft.nursing.di.contract;

import com.jqsoft.nursing.bean.grassroots_civil_administration.ReceptionListBean;
import com.jqsoft.nursing.bean.grassroots_civil_administration.base.GCAHttpResultBaseBean;

import java.util.List;



public interface QuestionActivityContract {
    interface View{

        void onLoadListSuccess(GCAHttpResultBaseBean<List<ReceptionListBean>> bean);
        void onLoadListFailure(String message);


    }

    interface  presenter{

    }
}
