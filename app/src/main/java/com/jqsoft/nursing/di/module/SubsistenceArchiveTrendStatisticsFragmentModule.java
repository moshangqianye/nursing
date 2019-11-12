package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.SubsistenceArchiveTrendStatisticsFragmentContract;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/5/21.
 */

@Module
public class SubsistenceArchiveTrendStatisticsFragmentModule {

    private SubsistenceArchiveTrendStatisticsFragmentContract.View view;

    public SubsistenceArchiveTrendStatisticsFragmentModule(SubsistenceArchiveTrendStatisticsFragmentContract.View view){
        this.view = view;
    }

    @FragmentScope
    @Provides
    public SubsistenceArchiveTrendStatisticsFragmentContract.View providerView(){
        return view;
    }

}
