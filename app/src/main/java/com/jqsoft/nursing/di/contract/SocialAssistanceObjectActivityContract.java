package com.jqsoft.nursing.di.contract;

import com.jqsoft.nursing.bean.BuildingRoomFloorBean;
import com.jqsoft.nursing.bean.base.HttpResultNurseBaseBean;
import com.jqsoft.nursing.bean.nursing.RoundsRecordCount;

import java.util.List;


public interface SocialAssistanceObjectActivityContract {
    interface View{

        void onLoadListSuccess(HttpResultNurseBaseBean<List<BuildingRoomFloorBean>> bean);
        void onLoadMoreListSuccess(HttpResultNurseBaseBean<List<BuildingRoomFloorBean>> bean);
        void onLoadListFailure(String message, boolean isLoadMore);

        void onLoadRoundsRecordCountSuccess(HttpResultNurseBaseBean<List<RoundsRecordCount>> bean);
        void onLoadMoreRoundsRecordCountSuccess(HttpResultNurseBaseBean<List<RoundsRecordCount>> bean);
        void onLoadRoundsRecordCountFailure(String message, boolean isLoadMore);

        void onLoadQrcodeSuccess(HttpResultNurseBaseBean<String> bean);
        void onLoadMoreQrcodeSuccess(HttpResultNurseBaseBean<String> bean);
        void onLoadQrcodeFailure(String message, boolean isLoadMore);

        //mac
        void onLoadmacSuccess(HttpResultNurseBaseBean<String> bean);
        void onLoadmacFailure(String message, boolean isLoadMore);


    }

    interface  presenter{

    }
}
