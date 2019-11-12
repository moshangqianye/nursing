package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.PersonCollectionActivityModule;
import com.jqsoft.nursing.di.ui.activity.PersonCollectionActivity;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Subcomponent;


@ActivityScope
@Subcomponent(modules = PersonCollectionActivityModule.class)
public interface PersonCollectionActivityComponent {
    void inject(PersonCollectionActivity PersonCollectionActivity);
}
