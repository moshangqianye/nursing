package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.SignServiceIncomeActivityContract;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/5/21.
 */

@Module
public class SignServiceIncomeActivityModule {

    private SignServiceIncomeActivityContract.View view;

    public SignServiceIncomeActivityModule(SignServiceIncomeActivityContract.View view){
        this.view = view;
    }

    @ActivityScope
    @Provides
    public SignServiceIncomeActivityContract.View providerView(){
        return view;
    }

}
