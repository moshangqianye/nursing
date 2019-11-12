package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.PendExecuContract;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/5/21.
 */

@Module
public class PendExecuActivityModule {

    private PendExecuContract.View view;

    public PendExecuActivityModule(PendExecuContract.View view){
        this.view = view;
    }

    @ActivityScope
    @Provides
    public PendExecuContract.View providerView(){
        return view;
    }

}
