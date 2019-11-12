package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.SocialHistoryActivityContract;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Jerry on 2017/12/27.
 */

@Module
public class SocialHistoryActivityModule {

    private SocialHistoryActivityContract.View view;

    public SocialHistoryActivityModule(SocialHistoryActivityContract.View view){
        this.view = view;
    }

    @ActivityScope
    @Provides
    public SocialHistoryActivityContract.View providerView(){
        return view;
    }

}
