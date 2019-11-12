package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.PolicyActivityContract;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/5/21.
 */

@Module
public class PolicyActivityModule {

    private PolicyActivityContract.View view;

    public PolicyActivityModule(PolicyActivityContract.View view){
        this.view = view;
    }

    @ActivityScope
    @Provides
    public PolicyActivityContract.View providerView(){
        return view;
    }

}
