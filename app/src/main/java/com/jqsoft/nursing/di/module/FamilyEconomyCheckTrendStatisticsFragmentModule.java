package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.FamilyEconomyCheckTrendStatisticsFragmentContract;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/5/21.
 */

@Module
public class FamilyEconomyCheckTrendStatisticsFragmentModule {

    private FamilyEconomyCheckTrendStatisticsFragmentContract.View view;

    public FamilyEconomyCheckTrendStatisticsFragmentModule(FamilyEconomyCheckTrendStatisticsFragmentContract.View view){
        this.view = view;
    }

    @FragmentScope
    @Provides
    public FamilyEconomyCheckTrendStatisticsFragmentContract.View providerView(){
        return view;
    }

}
