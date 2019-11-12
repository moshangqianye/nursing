package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.ReceptionDetailNewListActivityContract;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/5/21.
 */

@Module
public class ReceptionDetailNewListActivityModule {

    private ReceptionDetailNewListActivityContract.View view;

    public ReceptionDetailNewListActivityModule(ReceptionDetailNewListActivityContract.View view){
        this.view = view;
    }

    @ActivityScope
    @Provides
    public ReceptionDetailNewListActivityContract.View providerView(){
        return view;
    }

}
