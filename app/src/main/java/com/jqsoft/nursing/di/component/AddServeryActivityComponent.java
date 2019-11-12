package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.AddServeryActivityModule;
import com.jqsoft.nursing.di.ui.activity.AddServeryActivity;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Subcomponent;


@ActivityScope
@Subcomponent(modules = AddServeryActivityModule.class)
public interface AddServeryActivityComponent {
    void inject(AddServeryActivity addServeryActivity);
}
