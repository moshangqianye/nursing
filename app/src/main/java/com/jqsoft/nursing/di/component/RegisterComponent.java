package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.RegisterModule;
import com.jqsoft.nursing.di.ui.activity.RegisterActivity;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@ActivityScope
@Subcomponent(modules = RegisterModule.class)
public interface RegisterComponent {
    void inject(RegisterActivity registerActivity);
}
