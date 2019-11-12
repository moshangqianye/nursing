package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.SignServiceAssessActivityContract;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/5/21.
 */

@Module
public class SignServiceAssessActivityModule {

    private SignServiceAssessActivityContract.View view;

    public SignServiceAssessActivityModule(SignServiceAssessActivityContract.View view){
        this.view = view;
    }

    @ActivityScope
    @Provides
    public SignServiceAssessActivityContract.View providerView(){
        return view;
    }

}
