package com.jqsoft.nursing.di.module;
import com.jqsoft.nursing.di.contract.BpartFragmentContract;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/5/21.
 */

@Module
public class BFragmentModule {

    private BpartFragmentContract.View view;

    public BFragmentModule(BpartFragmentContract.View view){
        this.view = view;
    }

    @FragmentScope
    @Provides
    public BpartFragmentContract.View providerView(){
        return view;
    }

}