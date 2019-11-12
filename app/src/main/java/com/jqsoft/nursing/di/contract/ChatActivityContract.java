//package com.jqsoft.grassroots_civil_administration_platform.di.contract;
//
//import com.jqsoft.grassroots_civil_administration_platform.bean.response.ChatListWrapperBean;
//import com.jqsoft.grassroots_civil_administration_platform.bean.base.HttpResultBaseBean;
//import com.jqsoft.grassroots_civil_administration_platform.bean.base.HttpResultEmptyBean;
//
///**
// * Created by Administrator on 2017/5/21.
// */
//
//public interface ChatActivityContract {
//    interface View{
//
//        void onLoadListSuccess(HttpResultBaseBean<ChatListWrapperBean> bean);
//        void onLoadMoreListSuccess(HttpResultBaseBean<ChatListWrapperBean> bean);
//
//        void onLoadListFailure(String message, boolean isLoadMore);
//
//        void onSendMessageSuccess(HttpResultBaseBean<HttpResultEmptyBean> bean);
//        void onSendMessageFailure(String message);
//    }
//
//    interface  presenter{
//
//    }
//}
