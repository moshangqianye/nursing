package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.PeopleSignInfoFragmentContract;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/5/21.
 */

@Module
public class PeopleSignInfoFragmentModule {

    private PeopleSignInfoFragmentContract.View view;

    public PeopleSignInfoFragmentModule(PeopleSignInfoFragmentContract.View view){
        this.view = view;
    }

    @FragmentScope
    @Provides
    public PeopleSignInfoFragmentContract.View providerView(){
        return view;
    }

}
