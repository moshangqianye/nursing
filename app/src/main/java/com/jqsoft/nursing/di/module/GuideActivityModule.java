package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.GuideActivityContract;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/5/21.
 */

@Module
public class GuideActivityModule {

    private GuideActivityContract.View view;

    public GuideActivityModule(GuideActivityContract.View view){
        this.view = view;
    }

    @ActivityScope
    @Provides
    public GuideActivityContract.View providerView(){
        return view;
    }

}
