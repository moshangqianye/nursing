package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.PersonalInfoActivityModule;
import com.jqsoft.nursing.di.ui.activity.PersonalInfoActivity;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@ActivityScope
@Subcomponent(modules = PersonalInfoActivityModule.class)
public interface PersonalInfoActivityComponent {
    void inject(PersonalInfoActivity polityActivity);
}
