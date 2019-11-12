package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.HistoryDetailContract;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Jerry on 2017/12/27.
 */

@Module
public class HistoryDetailModule {

    private HistoryDetailContract.View view;

    public HistoryDetailModule(HistoryDetailContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    public HistoryDetailContract.View providerView() {
        return view;
    }

}
