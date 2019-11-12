package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.SubsistenceApproveTrendStatisticsFragmentContract;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/5/21.
 */

@Module
public class SubsistenceApproveTrendStatisticsFragmentModule {

    private SubsistenceApproveTrendStatisticsFragmentContract.View view;

    public SubsistenceApproveTrendStatisticsFragmentModule(SubsistenceApproveTrendStatisticsFragmentContract.View view){
        this.view = view;
    }

    @FragmentScope
    @Provides
    public SubsistenceApproveTrendStatisticsFragmentContract.View providerView(){
        return view;
    }

}
