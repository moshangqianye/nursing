package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.UrbanLowFamilyFragmentModule;
import com.jqsoft.nursing.di.ui.fragment.UrbanFuJianBianjiNewFragment;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@ActivityScope
@Subcomponent(modules = UrbanLowFamilyFragmentModule.class)
public interface UrbanLowFujianBianjiNewFragmentComponent {
    void inject(UrbanFuJianBianjiNewFragment policyActivity);
}