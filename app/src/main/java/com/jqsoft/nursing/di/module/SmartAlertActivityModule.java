package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.SmartAlertActivityContract;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/5/21.
 */

@Module
public class SmartAlertActivityModule {

    private SmartAlertActivityContract.View view;

    public SmartAlertActivityModule(SmartAlertActivityContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    public SmartAlertActivityContract.View providerView() {
        return view;
    }

}
