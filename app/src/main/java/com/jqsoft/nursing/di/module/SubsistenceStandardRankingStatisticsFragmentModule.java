package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.SubsistenceStandardRankingStatisticsFragmentContract;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/5/21.
 */

@Module
public class SubsistenceStandardRankingStatisticsFragmentModule {

    private SubsistenceStandardRankingStatisticsFragmentContract.View view;

    public SubsistenceStandardRankingStatisticsFragmentModule(SubsistenceStandardRankingStatisticsFragmentContract.View view){
        this.view = view;
    }

    @FragmentScope
    @Provides
    public SubsistenceStandardRankingStatisticsFragmentContract.View providerView(){
        return view;
    }

}
