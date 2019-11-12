package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.HouseHoldServeyActivityContract;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/5/21.
 */

@Module
public class HouseHoldServeyActivityModule {

    private HouseHoldServeyActivityContract.View view;

    public HouseHoldServeyActivityModule(HouseHoldServeyActivityContract.View view){
        this.view = view;
    }

    @ActivityScope
    @Provides
    public HouseHoldServeyActivityContract.View providerView(){
        return view;
    }

}
