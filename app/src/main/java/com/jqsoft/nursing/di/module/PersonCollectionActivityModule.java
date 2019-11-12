package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.PersonCollectionActivityContract;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Module;
import dagger.Provides;


@Module
public class PersonCollectionActivityModule {

    private PersonCollectionActivityContract.View view;

    public PersonCollectionActivityModule(PersonCollectionActivityContract.View view){
        this.view = view;
    }

    @ActivityScope
    @Provides
    public PersonCollectionActivityContract.View providerView(){
        return view;
    }

}
