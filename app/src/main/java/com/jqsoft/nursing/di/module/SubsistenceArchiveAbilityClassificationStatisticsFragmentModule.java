package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.SubsistenceArchiveAbilityClassificationStatisticsFragmentContract;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/5/21.
 */

@Module
public class SubsistenceArchiveAbilityClassificationStatisticsFragmentModule {

    private SubsistenceArchiveAbilityClassificationStatisticsFragmentContract.View view;

    public SubsistenceArchiveAbilityClassificationStatisticsFragmentModule(SubsistenceArchiveAbilityClassificationStatisticsFragmentContract.View view){
        this.view = view;
    }

    @FragmentScope
    @Provides
    public SubsistenceArchiveAbilityClassificationStatisticsFragmentContract.View providerView(){
        return view;
    }

}
