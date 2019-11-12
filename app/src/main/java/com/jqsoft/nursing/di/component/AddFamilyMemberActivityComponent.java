package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.AddFamilyMemberActivityModule;
import com.jqsoft.nursing.di.ui.activity.AddFamilyMemberActivity;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@ActivityScope
@Subcomponent(modules = AddFamilyMemberActivityModule.class)
public interface AddFamilyMemberActivityComponent {
    void inject(AddFamilyMemberActivity addFamilyMemberActivity);
}
