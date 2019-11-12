package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.FamilyMemberActivityContract;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/5/21.
 */

@Module
public class FamilyMemberActivityModule {

    private FamilyMemberActivityContract.View view;

    public FamilyMemberActivityModule(FamilyMemberActivityContract.View view){
        this.view = view;
    }

    @ActivityScope
    @Provides
    public FamilyMemberActivityContract.View providerView(){
        return view;
    }

}
