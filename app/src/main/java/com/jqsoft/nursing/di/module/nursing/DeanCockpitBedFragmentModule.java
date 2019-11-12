package com.jqsoft.nursing.di.module.nursing;

import com.jqsoft.nursing.di.contract.nursing.DeanCockpitBedFragmentContract;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/5/21.
 */

@Module
public class DeanCockpitBedFragmentModule {

    private DeanCockpitBedFragmentContract.View view;

    public DeanCockpitBedFragmentModule(DeanCockpitBedFragmentContract.View view){
        this.view = view;
    }

    @FragmentScope
    @Provides
    public DeanCockpitBedFragmentContract.View providerView(){
        return view;
    }

}