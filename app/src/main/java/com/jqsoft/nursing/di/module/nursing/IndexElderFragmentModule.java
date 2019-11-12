package com.jqsoft.nursing.di.module.nursing;


import com.jqsoft.nursing.di.contract.nursing.IndexElderFragmentContract;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/5/21.
 */

@Module
public class IndexElderFragmentModule {

    private IndexElderFragmentContract.View view;

    public IndexElderFragmentModule(IndexElderFragmentContract.View view){
        this.view = view;
    }

    @FragmentScope
    @Provides
    public IndexElderFragmentContract.View providerView(){
        return view;
    }

}