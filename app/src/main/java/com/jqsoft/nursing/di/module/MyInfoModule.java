package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.MyInfoContract;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/5/21.
 */

@Module
public class MyInfoModule {

    private MyInfoContract.View view;

    public MyInfoModule(MyInfoContract.View view){
        this.view = view;
    }

    @ActivityScope
    @Provides
    public MyInfoContract.View providerView(){
        return view;
    }

}