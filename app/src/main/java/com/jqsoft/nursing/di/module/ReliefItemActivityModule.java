package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.ReliefItemActivityContract;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/5/21.
 */

@Module
public class ReliefItemActivityModule {

    private ReliefItemActivityContract.View view;

    public ReliefItemActivityModule(ReliefItemActivityContract.View view){
        this.view = view;
    }

    @ActivityScope
    @Provides
    public ReliefItemActivityContract.View providerView(){
        return view;
    }

}
