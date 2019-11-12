package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.SocialHistoryActivityModule;
import com.jqsoft.nursing.di.ui.activity.SocialHistoryActivity;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Subcomponent;


@ActivityScope
@Subcomponent(modules = SocialHistoryActivityModule.class)
public interface SocialHistoryActivityComponent {
    void inject(SocialHistoryActivity socialHistoryActivity);
}
