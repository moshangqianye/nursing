package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.InstitutionCharacterClassificationStatisticsFragmentContract;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/5/21.
 */

@Module
public class InstitutionCharacterClassificationStatisticsFragmentModule {

    private InstitutionCharacterClassificationStatisticsFragmentContract.View view;

    public InstitutionCharacterClassificationStatisticsFragmentModule(InstitutionCharacterClassificationStatisticsFragmentContract.View view){
        this.view = view;
    }

    @FragmentScope
    @Provides
    public InstitutionCharacterClassificationStatisticsFragmentContract.View providerView(){
        return view;
    }

}
