package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.SignServiceAssessFragmentContract;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/5/21.
 */

@Module
public class SignServiceAssessFragmentModule {

    private SignServiceAssessFragmentContract.View view;

    public SignServiceAssessFragmentModule(SignServiceAssessFragmentContract.View view){
        this.view = view;
    }

    @FragmentScope
    @Provides
    public SignServiceAssessFragmentContract.View providerView(){
        return view;
    }

}
