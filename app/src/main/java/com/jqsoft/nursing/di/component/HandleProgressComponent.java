package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.HandleProgressModule;
import com.jqsoft.nursing.di.ui.activity.HandleProgress;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Subcomponent;


@ActivityScope
@Subcomponent(modules = HandleProgressModule.class)
public interface HandleProgressComponent {
    void inject(HandleProgress handleProgress);
}
