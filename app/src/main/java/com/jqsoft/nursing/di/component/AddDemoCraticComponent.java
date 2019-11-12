package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.AddDemocraticModule;
import com.jqsoft.nursing.di.ui.activity.AddDemocraticActivity;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Subcomponent;


@ActivityScope
@Subcomponent(modules = AddDemocraticModule.class)
public interface AddDemoCraticComponent {
    void inject(AddDemocraticActivity addDemocraticActivity);
}
