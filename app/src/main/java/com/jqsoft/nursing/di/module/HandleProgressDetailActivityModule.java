package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.HandleProgressDetailActivityContract;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Jerry on 2017/12/27.
 */

@Module
public class HandleProgressDetailActivityModule {

    private HandleProgressDetailActivityContract.View view;

    public HandleProgressDetailActivityModule(HandleProgressDetailActivityContract.View view){
        this.view = view;
    }

    @ActivityScope
    @Provides
    public HandleProgressDetailActivityContract.View providerView(){
        return view;
    }

}
