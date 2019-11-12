package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.SocialAssistanceObjectActivityContract;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Jerry on 2017/12/27.
 */

@Module
public class SocialAssistanceObjectActivityModule {

    private SocialAssistanceObjectActivityContract.View view;

    public SocialAssistanceObjectActivityModule(SocialAssistanceObjectActivityContract.View view){
        this.view = view;
    }

    @ActivityScope
    @Provides
    public SocialAssistanceObjectActivityContract.View providerView(){
        return view;
    }

}
