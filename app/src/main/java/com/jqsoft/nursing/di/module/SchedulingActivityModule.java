package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.SchedulingActivityContract;
import com.jqsoft.nursing.di.contract.SchedulingFragmentContract;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Module;
import dagger.Provides;


@Module
public class SchedulingActivityModule {

    private SchedulingActivityContract.View view;

    public SchedulingActivityModule(SchedulingActivityContract.View view){
        this.view = view;
    }

    @ActivityScope
    @Provides
    public SchedulingActivityContract.View providerView(){
        return view;
    }

}
