package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.MedicalPersonDirectoryActivityModule;
import com.jqsoft.nursing.di.ui.activity.MedicalPersonDirectoryActivity;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Subcomponent;


@ActivityScope
@Subcomponent(modules = MedicalPersonDirectoryActivityModule.class)
public interface MedicalPersonDirectoryActivityComponent {
    void inject(MedicalPersonDirectoryActivity directoryActivity);
}
