package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.ReserverContract;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/5/21.
 */

@Module
public class ReserverActivityModule {

    private ReserverContract.View view;

    public ReserverActivityModule(ReserverContract.View view){
        this.view = view;
    }

    @ActivityScope
    @Provides
    public ReserverContract.View providerView(){
        return view;
    }

}
