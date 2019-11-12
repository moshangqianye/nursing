package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.SignSeverPakesContract;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/5/21.
 */

@Module
public class SignSeverPakesActivityModule {

    private SignSeverPakesContract.View view;

    public SignSeverPakesActivityModule(SignSeverPakesContract.View view){
        this.view = view;
    }

    @ActivityScope
    @Provides
    public SignSeverPakesContract.View providerView(){
        return view;
    }

}
