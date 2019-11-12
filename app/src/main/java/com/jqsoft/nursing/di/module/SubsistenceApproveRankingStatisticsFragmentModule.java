package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.SubsistenceApproveRankingStatisticsFragmentContract;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/5/21.
 */

@Module
public class SubsistenceApproveRankingStatisticsFragmentModule {

    private SubsistenceApproveRankingStatisticsFragmentContract.View view;

    public SubsistenceApproveRankingStatisticsFragmentModule(SubsistenceApproveRankingStatisticsFragmentContract.View view){
        this.view = view;
    }

    @FragmentScope
    @Provides
    public SubsistenceApproveRankingStatisticsFragmentContract.View providerView(){
        return view;
    }

}
