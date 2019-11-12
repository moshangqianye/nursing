package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.ReceptionActivityContract;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Module;
import dagger.Provides;


@Module
public class ReceptionActivityModule {

    private ReceptionActivityContract.View view;

    public ReceptionActivityModule(ReceptionActivityContract.View view){
        this.view = view;
    }

    @ActivityScope
    @Provides
    public ReceptionActivityContract.View providerView(){
        return view;
    }

}
