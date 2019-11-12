package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.ReceptionActivityModule;
import com.jqsoft.nursing.di.ui.activity.ReceptionActivity;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Subcomponent;


@ActivityScope
@Subcomponent(modules = ReceptionActivityModule.class)
public interface ReceptionActivityComponent {
    void inject( ReceptionActivity ReceptionActivity);
}
