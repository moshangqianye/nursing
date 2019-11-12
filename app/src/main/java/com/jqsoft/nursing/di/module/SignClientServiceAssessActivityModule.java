package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.SignClientServiceAssessActivityContract;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/5/21.
 */

@Module
public class SignClientServiceAssessActivityModule {

    private SignClientServiceAssessActivityContract.View view;

    public SignClientServiceAssessActivityModule(SignClientServiceAssessActivityContract.View view){
        this.view = view;
    }

    @ActivityScope
    @Provides
    public SignClientServiceAssessActivityContract.View providerView(){
        return view;
    }

}
