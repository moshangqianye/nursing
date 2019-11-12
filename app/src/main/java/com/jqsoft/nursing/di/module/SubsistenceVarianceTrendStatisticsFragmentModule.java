package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.SubsistenceVarianceTrendStatisticsFragmentContract;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/5/21.
 */

@Module
public class SubsistenceVarianceTrendStatisticsFragmentModule {

    private SubsistenceVarianceTrendStatisticsFragmentContract.View view;

    public SubsistenceVarianceTrendStatisticsFragmentModule(SubsistenceVarianceTrendStatisticsFragmentContract.View view){
        this.view = view;
    }

    @FragmentScope
    @Provides
    public SubsistenceVarianceTrendStatisticsFragmentContract.View providerView(){
        return view;
    }

}
