package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.ChangePasswordContract;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Module;
import dagger.Provides;


@Module
public class ChangePasswordActivityModule {

    private ChangePasswordContract.View view;

    public ChangePasswordActivityModule(ChangePasswordContract.View view){
        this.view = view;
    }

    @ActivityScope
    @Provides
    public ChangePasswordContract.View providerView(){
        return view;
    }

}
