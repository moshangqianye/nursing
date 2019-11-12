package com.jqsoft.nursing.di.module.nursing;

import com.jqsoft.nursing.di.contract.nursing.SpecificNursingFragmentContract;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/5/21.
 */

@Module
public class SpecificNursingFragmentModule {

    private SpecificNursingFragmentContract.View view;

    public SpecificNursingFragmentModule(SpecificNursingFragmentContract.View view){
        this.view = view;
    }

    @FragmentScope
    @Provides
    public SpecificNursingFragmentContract.View providerView(){
        return view;
    }

}