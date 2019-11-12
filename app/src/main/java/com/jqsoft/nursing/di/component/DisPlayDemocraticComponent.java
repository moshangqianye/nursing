package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.DisPlayDemocraticModule;
import com.jqsoft.nursing.di.ui.activity.DisPlayDemocraticActivity;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Subcomponent;


@ActivityScope
@Subcomponent(modules = DisPlayDemocraticModule.class)
public interface DisPlayDemocraticComponent {
    void inject(DisPlayDemocraticActivity disPlayDemocraticActivity);
}
