package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.PeopleBaseFragmentContract;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/5/21.
 */

@Module
public class PeopleBaseInfoFragmentModule {

    private PeopleBaseFragmentContract.View view;

    public PeopleBaseInfoFragmentModule(PeopleBaseFragmentContract.View view){
        this.view = view;
    }

    @FragmentScope
    @Provides
    public PeopleBaseFragmentContract.View providerView(){
        return view;
    }

}
