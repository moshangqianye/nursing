package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.FamilyMemberActivityModule;
import com.jqsoft.nursing.di.ui.activity.FamilyMemberActivity;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@ActivityScope
@Subcomponent(modules = FamilyMemberActivityModule.class)
public interface FamilyMemberActivityComponent {
    void inject(FamilyMemberActivity familyMemberActivity);
}
