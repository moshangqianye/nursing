package com.jqsoft.nursing.di.module.nursing;

import com.jqsoft.nursing.di.contract.nursing.DeanCockpitElderFragmentContract;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/5/21.
 */

@Module
public class DeanCockpitElderFragmentModule {

    private DeanCockpitElderFragmentContract.View view;

    public DeanCockpitElderFragmentModule(DeanCockpitElderFragmentContract.View view){
        this.view = view;
    }

    @FragmentScope
    @Provides
    public DeanCockpitElderFragmentContract.View providerView(){
        return view;
    }

}