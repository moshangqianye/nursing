package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.HandleFindContract;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/5/21.
 */

@Module
public class HandleFindModule {

    private HandleFindContract.View view;

    public HandleFindModule(HandleFindContract.View view){
        this.view = view;
    }

    @ActivityScope
    @Provides
    public HandleFindContract.View providerView(){
        return view;
    }

}