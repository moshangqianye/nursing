package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.SchedulingFragmentContract;
import com.jqsoft.nursing.di.contract.UseCollectionFragmentContract;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Module;
import dagger.Provides;


@Module
public class SchedulingFragmentModule {

    private SchedulingFragmentContract.View view;

    public SchedulingFragmentModule(SchedulingFragmentContract.View view){
        this.view = view;
    }

    @ActivityScope
    @Provides
    public SchedulingFragmentContract.View providerView(){
        return view;
    }

}
