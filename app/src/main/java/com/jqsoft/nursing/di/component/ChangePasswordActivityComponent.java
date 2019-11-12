package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.ChangePasswordActivityModule;
import com.jqsoft.nursing.di.ui.activity.ChangePasswordActivity;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Subcomponent;



@ActivityScope
@Subcomponent(modules = ChangePasswordActivityModule.class)
public interface ChangePasswordActivityComponent {
    void inject(ChangePasswordActivity ChangePasswordActivity);
}
