package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.PolityActivityContract;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/5/21.
 */

@Module
public class PolityActivityModule {

    private PolityActivityContract.View view;

    public PolityActivityModule(PolityActivityContract.View view){
        this.view = view;
    }

    @ActivityScope
    @Provides
    public PolityActivityContract.View providerView(){
        return view;
    }

}
