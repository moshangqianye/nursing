package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.FamilyDetailsActivityModule;
import com.jqsoft.nursing.di.ui.activity.FamilyDetailActivity;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Subcomponent;


@ActivityScope
@Subcomponent(modules = FamilyDetailsActivityModule.class)
public interface FamilyDetailActivityComponent {
    void inject(FamilyDetailActivity familyDetailActivity);
}
