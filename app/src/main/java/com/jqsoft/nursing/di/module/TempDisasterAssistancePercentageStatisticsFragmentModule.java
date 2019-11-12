package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.TempDisasterAssistancePercentageStatisticsFragmentContract;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/5/21.
 */

@Module
public class TempDisasterAssistancePercentageStatisticsFragmentModule {

    private TempDisasterAssistancePercentageStatisticsFragmentContract.View view;

    public TempDisasterAssistancePercentageStatisticsFragmentModule(TempDisasterAssistancePercentageStatisticsFragmentContract.View view){
        this.view = view;
    }

    @FragmentScope
    @Provides
    public TempDisasterAssistancePercentageStatisticsFragmentContract.View providerView(){
        return view;
    }

}
