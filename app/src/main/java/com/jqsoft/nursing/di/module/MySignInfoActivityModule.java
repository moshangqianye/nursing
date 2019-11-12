package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.MySignInfoActivityContract;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/5/21.
 */

@Module
public class MySignInfoActivityModule {

    private MySignInfoActivityContract.View view;

    public MySignInfoActivityModule(MySignInfoActivityContract.View view){
        this.view = view;
    }

    @ActivityScope
    @Provides
    public MySignInfoActivityContract.View providerView(){
        return view;
    }

}