package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.VillageLevelMedicalInstitutionDirectoryActivityModule;
import com.jqsoft.nursing.di.ui.activity.VillageLevelMedicalInstitutionDirectoryActivity;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@ActivityScope
@Subcomponent(modules = VillageLevelMedicalInstitutionDirectoryActivityModule.class)
public interface VillageLevelMedicalInstitutionDirectoryActivityComponent {
    void inject(VillageLevelMedicalInstitutionDirectoryActivity directoryActivity);
}
