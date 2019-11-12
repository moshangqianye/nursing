package com.jqsoft.nursing.di.contract;

import com.jqsoft.nursing.bean.grassroots_civil_administration.FamilyEconomyCheckProjectCheckBean;
import com.jqsoft.nursing.bean.grassroots_civil_administration.base.GCAHttpResultBaseBean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/21.
 */

public interface FamilyEconomyCheckProjectCheckStatisticsFragmentContract {
    interface View{

        void onLoadListSuccess(GCAHttpResultBaseBean<List<FamilyEconomyCheckProjectCheckBean>> bean);

        void onLoadListFailure(String message);


    }

    interface  presenter{

    }
}
