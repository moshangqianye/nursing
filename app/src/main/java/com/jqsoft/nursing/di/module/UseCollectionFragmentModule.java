package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.UseCollectionFragmentContract;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Module;
import dagger.Provides;


@Module
public class UseCollectionFragmentModule {

    private UseCollectionFragmentContract.View view;

    public UseCollectionFragmentModule(UseCollectionFragmentContract.View view){
        this.view = view;
    }

    @ActivityScope
    @Provides
    public UseCollectionFragmentContract.View providerView(){
        return view;
    }

}
