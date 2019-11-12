package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.SignServiceIncomeActivityModule;
import com.jqsoft.nursing.di.ui.activity.SignServiceIncomeActivity;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Subcomponent;


@ActivityScope
@Subcomponent(modules = SignServiceIncomeActivityModule.class)
public interface SignServiceIncomeActivityComponent {
    void inject(SignServiceIncomeActivity signServiceIncomeActivity);
}
