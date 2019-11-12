package com.jqsoft.nursing.di.contract;

import com.jqsoft.nursing.bean.base.HttpResultBaseBean;
import com.jqsoft.nursing.bean.response_new.IntelligentHonourAgreementOverviewResultBean;

/**
 * Created by Administrator on 2017/5/21.
 */

public interface IntelligentHonourAgreementRemindActivityContract {
    interface View{

        void onLoadIntelligentHonourAgreementOverviewDataSuccess(HttpResultBaseBean<IntelligentHonourAgreementOverviewResultBean> bean);

        void onLoadIntelligentHonourAgreementOverviewDataFailure(String message);
    }

    interface  presenter{

    }
}
