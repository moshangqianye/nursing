package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.SubsistenceAccountRankingStatisticsFragmentContract;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/5/21.
 */

@Module
public class SubsistenceAccountRankingStatisticsFragmentModule {

    private SubsistenceAccountRankingStatisticsFragmentContract.View view;

    public SubsistenceAccountRankingStatisticsFragmentModule(SubsistenceAccountRankingStatisticsFragmentContract.View view){
        this.view = view;
    }

    @FragmentScope
    @Provides
    public SubsistenceAccountRankingStatisticsFragmentContract.View providerView(){
        return view;
    }

}
