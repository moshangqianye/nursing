package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.IgGuideActivityContract;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/5/21.
 */

@Module
public class IgGuideActivityModule {

    private IgGuideActivityContract.View view;

    public IgGuideActivityModule(IgGuideActivityContract.View view){
        this.view = view;
    }

    @ActivityScope
    @Provides
    public IgGuideActivityContract.View providerView(){
        return view;
    }
}
