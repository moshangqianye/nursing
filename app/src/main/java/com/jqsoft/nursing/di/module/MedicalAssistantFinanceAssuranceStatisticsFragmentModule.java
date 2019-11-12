package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.MedicalAssistantFinanceAssuranceStatisticsFragmentContract;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/5/21.
 */

@Module
public class MedicalAssistantFinanceAssuranceStatisticsFragmentModule {

    private MedicalAssistantFinanceAssuranceStatisticsFragmentContract.View view;

    public MedicalAssistantFinanceAssuranceStatisticsFragmentModule(MedicalAssistantFinanceAssuranceStatisticsFragmentContract.View view){
        this.view = view;
    }

    @FragmentScope
    @Provides
    public MedicalAssistantFinanceAssuranceStatisticsFragmentContract.View providerView(){
        return view;
    }

}
