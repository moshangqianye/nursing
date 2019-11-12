package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.SocialDetailActivityContract;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Jerry on 2017/12/27.
 */

@Module
public class SocialDetailActivityModule {

    private SocialDetailActivityContract.View view;

    public SocialDetailActivityModule(SocialDetailActivityContract.View view){
        this.view = view;
    }

    @ActivityScope
    @Provides
    public SocialDetailActivityContract.View providerView(){
        return view;
    }

}
