package com.jqsoft.nursing.di.contract;

import com.jqsoft.nursing.bean.SocialListHistoryBean;
import com.jqsoft.nursing.bean.grassroots_civil_administration.base.GCAHttpResultBaseBean;

import java.util.List;


public interface SocialHistoryActivityContract {
    interface View{

        void onLoadListSuccess(GCAHttpResultBaseBean<List<SocialListHistoryBean>> bean);
        void onLoadMoreListSuccess(GCAHttpResultBaseBean<List<SocialListHistoryBean>> bean);
        void onLoadListFailure(String message, boolean isLoadMore);

    }

    interface  presenter{

    }
}
