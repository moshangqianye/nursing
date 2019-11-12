package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.FamilyEconomyCheckShareIndexStatisticsFragmentContract;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/5/21.
 */

@Module
public class FamilyEconomyCheckShareIndexStatisticsFragmentModule {

    private FamilyEconomyCheckShareIndexStatisticsFragmentContract.View view;

    public FamilyEconomyCheckShareIndexStatisticsFragmentModule(FamilyEconomyCheckShareIndexStatisticsFragmentContract.View view){
        this.view = view;
    }

    @FragmentScope
    @Provides
    public FamilyEconomyCheckShareIndexStatisticsFragmentContract.View providerView(){
        return view;
    }

}
