package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.ArcFaceListActivityModule;
import com.jqsoft.nursing.di.module.MedicalInstitutionActivityModule;
import com.jqsoft.nursing.di.ui.activity.ArcFaceListActivity;
import com.jqsoft.nursing.di.ui.activity.MedicalInstitutionActivity;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@ActivityScope
@Subcomponent(modules = ArcFaceListActivityModule.class)
public interface ArcFaceListActivityComponent {
    void inject(ArcFaceListActivity  activity);
}
