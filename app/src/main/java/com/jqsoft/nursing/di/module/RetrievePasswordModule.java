package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.RetrievePasswordContract;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/5/21.
 */

@Module
public class RetrievePasswordModule {

    private RetrievePasswordContract.View view;

    public RetrievePasswordModule(RetrievePasswordContract.View view){
        this.view = view;
    }

    @ActivityScope
    @Provides
    public RetrievePasswordContract.View providerView(){
        return view;
    }

}