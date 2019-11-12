package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.TempDisasterAssistantStatisticsFragmentContract;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/5/21.
 */

@Module
public class TempDisasterAssistantStatisticsFragmentModule {

    private TempDisasterAssistantStatisticsFragmentContract.View view;

    public TempDisasterAssistantStatisticsFragmentModule(TempDisasterAssistantStatisticsFragmentContract.View view){
        this.view = view;
    }

    @FragmentScope
    @Provides
    public TempDisasterAssistantStatisticsFragmentContract.View providerView(){
        return view;
    }

}
