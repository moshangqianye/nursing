package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.AccessFileContract;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/5/21.
 */

@Module
public class UpdatePeopleModule {

    private AccessFileContract.View view;

    public UpdatePeopleModule(AccessFileContract.View view){
        this.view = view;
    }

    @FragmentScope
    @Provides
    public AccessFileContract.View providerView(){
        return view;
    }

}
