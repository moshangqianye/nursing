package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.DemocraticAppraisalContract;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/5/21.
 */

@Module
public class DemocraticAppraisalModule {

    private DemocraticAppraisalContract.View view;

    public DemocraticAppraisalModule(DemocraticAppraisalContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    public DemocraticAppraisalContract.View providerView() {
        return view;
    }

}
