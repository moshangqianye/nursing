package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.PolicyActivityModule;
import com.jqsoft.nursing.di.ui.activity.PolicyActivity;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@ActivityScope
@Subcomponent(modules = PolicyActivityModule.class)
public interface PolicyActivityComponent {
    void inject(PolicyActivity policyActivity);
}
