package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.SubsistenceApprovePovertyReasonStatisticsFragmentContract;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/5/21.
 */

@Module
public class SubsistenceApprovePovertyReasonStatisticsFragmentModule {

    private SubsistenceApprovePovertyReasonStatisticsFragmentContract.View view;

    public SubsistenceApprovePovertyReasonStatisticsFragmentModule(SubsistenceApprovePovertyReasonStatisticsFragmentContract.View view){
        this.view = view;
    }

    @FragmentScope
    @Provides
    public SubsistenceApprovePovertyReasonStatisticsFragmentContract.View providerView(){
        return view;
    }

}
