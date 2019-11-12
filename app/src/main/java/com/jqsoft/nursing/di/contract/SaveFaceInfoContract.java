package com.jqsoft.nursing.di.contract;

import com.jqsoft.nursing.bean.base.HttpResultBaseBean;
import com.jqsoft.nursing.bean.base.HttpResultNewBaseBean;
import com.jqsoft.nursing.bean.grassroots_civil_administration.SRCLoginDataDictionaryBean;
import com.jqsoft.nursing.bean.grassroots_civil_administration.SRCLoginSalvationBean;
import com.jqsoft.nursing.bean.resident.SRCLoginAreaBean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/21.
 */

public interface SaveFaceInfoContract {
    interface View{

        void onSaveFaceInfoSuccess(HttpResultNewBaseBean<String> bean);

        void onSaveFaceInfoFailure(String message);


    }

    interface  presenter{

    }
}
