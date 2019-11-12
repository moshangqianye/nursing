package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.UrbanAddFamilyContract;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/5/21.
 */

@Module
public class UrbanLowAddFamilyActivityModule {

    private UrbanAddFamilyContract.View view;

    public UrbanLowAddFamilyActivityModule(UrbanAddFamilyContract.View view){
        this.view = view;
    }

    @ActivityScope
    @Provides
    public UrbanAddFamilyContract.View providerView(){
        return view;
    }

}
