//package com.jqsoft.grassroots_civil_administration_platform.di.module;
//
//import com.jqsoft.nursing.di.contract.ChatActivityContract;
//import com.jqsoft.nursing.di_app.ActivityScope;
//
//import dagger.Module;
//import dagger.Provides;
//
///**
// * Created by Administrator on 2017/5/21.
// */
//
//@Module
//public class ChatActivityModule {
//
//    private ChatActivityContract.View view;
//
//    public ChatActivityModule(ChatActivityContract.View view){
//        this.view = view;
//    }
//
//    @ActivityScope
//    @Provides
//    public ChatActivityContract.View providerView(){
//        return view;
//    }
//
//}
