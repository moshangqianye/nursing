package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.LoginModule;
import com.jqsoft.nursing.di.ui.activity.LoginActivityNew;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@ActivityScope
@Subcomponent(modules = LoginModule.class)
public interface LoginComponent {
    void inject(LoginActivityNew loginActivity);
//    void inject(LoginActivity loginActivity);
}
