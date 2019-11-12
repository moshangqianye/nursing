package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.AddFamilyMemberActivityContract;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/5/21.
 */

@Module
public class AddFamilyMemberActivityModule {

    private AddFamilyMemberActivityContract.View view;

    public AddFamilyMemberActivityModule(AddFamilyMemberActivityContract.View view){
        this.view = view;
    }

    @ActivityScope
    @Provides
    public AddFamilyMemberActivityContract.View providerView(){
        return view;
    }

}