package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.ReceptionDetailActivityContract;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/5/21.
 */

@Module
public class ReceptionDetailActivityModule {

    private ReceptionDetailActivityContract.View view;

    public ReceptionDetailActivityModule(ReceptionDetailActivityContract.View view){
        this.view = view;
    }

    @ActivityScope
    @Provides
    public ReceptionDetailActivityContract.View providerView(){
        return view;
    }

}
