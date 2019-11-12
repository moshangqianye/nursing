package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.UrbanLowAddFamilyActivityModule;
import com.jqsoft.nursing.di.ui.activity.UrbanFamilybianjiStatusActivity;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@ActivityScope
@Subcomponent(modules = UrbanLowAddFamilyActivityModule.class)
public interface UrbanLowAddFamilyBianjiStatusActivityComponent {
    void inject(UrbanFamilybianjiStatusActivity policyActivity);
}
