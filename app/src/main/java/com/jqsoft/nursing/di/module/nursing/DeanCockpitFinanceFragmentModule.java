package com.jqsoft.nursing.di.module.nursing;

import com.jqsoft.nursing.di.contract.nursing.DeanCockpitFinanceFragmentContract;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/5/21.
 */

@Module
public class DeanCockpitFinanceFragmentModule {

    private DeanCockpitFinanceFragmentContract.View view;

    public DeanCockpitFinanceFragmentModule(DeanCockpitFinanceFragmentContract.View view){
        this.view = view;
    }

    @FragmentScope
    @Provides
    public DeanCockpitFinanceFragmentContract.View providerView(){
        return view;
    }

}