package com.jqsoft.nursing.di.contract;

import com.jqsoft.nursing.bean.FamilyMemberListBean;
import com.jqsoft.nursing.bean.grassroots_civil_administration.base.GCAHttpResultBaseBean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/21.
 */

public interface HouseHoldFaimilyMemberContract {
    interface View {

        void onLoadListSuccess(GCAHttpResultBaseBean<List<FamilyMemberListBean>> bean);

        void onLoadMoreListSuccess(GCAHttpResultBaseBean<List<FamilyMemberListBean>> bean);

        void onLoadListFailure(String message);


    }

    interface presenter {

    }
}
