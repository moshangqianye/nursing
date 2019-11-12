package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.MyMessageActivityContract;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/5/21.
 */

@Module
public class MyMessageActivityModule {

    private MyMessageActivityContract.View view;

    public MyMessageActivityModule(MyMessageActivityContract.View view){
        this.view = view;
    }

    @ActivityScope
    @Provides
    public MyMessageActivityContract.View providerView(){
        return view;
    }

}
