package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.HandleProgressDetailActivityModule;
import com.jqsoft.nursing.di.ui.activity.HandleProgressDetailActivity;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Subcomponent;


@ActivityScope
@Subcomponent(modules = HandleProgressDetailActivityModule.class)
public interface HandleProgressDetailActivityComponent {
    void inject(HandleProgressDetailActivity handleProgressDetailActivity);
}
