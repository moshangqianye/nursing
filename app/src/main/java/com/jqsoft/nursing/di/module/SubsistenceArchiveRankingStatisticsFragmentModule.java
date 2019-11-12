package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.SubsistenceArchiveRankingStatisticsFragmentContract;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/5/21.
 */

@Module
public class SubsistenceArchiveRankingStatisticsFragmentModule {

    private SubsistenceArchiveRankingStatisticsFragmentContract.View view;

    public SubsistenceArchiveRankingStatisticsFragmentModule(SubsistenceArchiveRankingStatisticsFragmentContract.View view){
        this.view = view;
    }

    @FragmentScope
    @Provides
    public SubsistenceArchiveRankingStatisticsFragmentContract.View providerView(){
        return view;
    }

}
