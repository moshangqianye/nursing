package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.FamilDetailActivityContract;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Jerry on 2017/12/27.
 */

@Module
public class FamilyDetailsActivityModule {

    private FamilDetailActivityContract.View view;

    public FamilyDetailsActivityModule(FamilDetailActivityContract.View view){
        this.view = view;
    }

    @ActivityScope
    @Provides
    public FamilDetailActivityContract.View providerView(){
        return view;
    }

}
