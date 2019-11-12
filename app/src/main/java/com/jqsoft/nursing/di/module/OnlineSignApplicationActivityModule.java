package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.OnLineSignApplicationContract;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/5/21.
 */

@Module
public class OnlineSignApplicationActivityModule {

    private OnLineSignApplicationContract.View view;

    public OnlineSignApplicationActivityModule(OnLineSignApplicationContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    public OnLineSignApplicationContract.View providerView() {
        return view;
    }

}
