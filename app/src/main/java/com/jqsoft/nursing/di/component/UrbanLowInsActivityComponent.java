package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.UrbanLowInsActivityModule;
import com.jqsoft.nursing.di.ui.activity.UrbanLowInsuranceActivity;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@ActivityScope
@Subcomponent(modules = UrbanLowInsActivityModule.class)
public interface UrbanLowInsActivityComponent {
    void inject(UrbanLowInsuranceActivity policyActivity);
}
