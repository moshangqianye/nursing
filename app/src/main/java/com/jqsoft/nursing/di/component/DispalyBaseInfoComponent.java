package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.DispalyInfoActivityModule;
import com.jqsoft.nursing.di.ui.activity.AddImgVideoServeryActivity;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Subcomponent;


@ActivityScope
@Subcomponent(modules = DispalyInfoActivityModule.class)
public interface DispalyBaseInfoComponent {
    void inject(AddImgVideoServeryActivity addServeryActivity);
}
