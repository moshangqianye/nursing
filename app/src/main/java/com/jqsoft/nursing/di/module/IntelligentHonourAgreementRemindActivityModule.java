package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.IntelligentHonourAgreementRemindActivityContract;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/5/21.
 */

@Module
public class IntelligentHonourAgreementRemindActivityModule {

    private IntelligentHonourAgreementRemindActivityContract.View view;

    public IntelligentHonourAgreementRemindActivityModule(IntelligentHonourAgreementRemindActivityContract.View view){
        this.view = view;
    }

    @ActivityScope
    @Provides
    public IntelligentHonourAgreementRemindActivityContract.View providerView(){
        return view;
    }

}