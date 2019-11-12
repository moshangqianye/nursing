package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.ServicePackDetailContract;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/5/21.
 */

@Module
public class ServicePackDetailActivityModule {

    private ServicePackDetailContract.View view;

    public ServicePackDetailActivityModule(ServicePackDetailContract.View view){
        this.view = view;
    }

    @ActivityScope
    @Provides
    public ServicePackDetailContract.View providerView(){
        return view;
    }

}
