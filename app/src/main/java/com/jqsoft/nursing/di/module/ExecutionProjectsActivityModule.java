package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.ExecutionProjectsActivityContract;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/5/21.
 */

@Module
public class ExecutionProjectsActivityModule {

    private ExecutionProjectsActivityContract.View view;

    public ExecutionProjectsActivityModule(ExecutionProjectsActivityContract.View view){
        this.view = view;
    }

    @ActivityScope
    @Provides
    public ExecutionProjectsActivityContract.View providerView(){
        return view;
    }

}
