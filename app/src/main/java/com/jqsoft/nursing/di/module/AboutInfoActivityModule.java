package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.AboutInfoContract;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Module;
import dagger.Provides;


@Module
public class AboutInfoActivityModule {

    private AboutInfoContract.View view;

    public AboutInfoActivityModule(AboutInfoContract.View view){
        this.view = view;
    }

    @ActivityScope
    @Provides
    public AboutInfoContract.View providerView(){
        return view;
    }

}
