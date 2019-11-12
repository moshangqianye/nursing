package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.SocialDetailActivityModule;
import com.jqsoft.nursing.di.ui.activity.SocialDetailActivity;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Subcomponent;


@ActivityScope
@Subcomponent(modules = SocialDetailActivityModule.class)
public interface SocialDetailActivityComponent {
    void inject(SocialDetailActivity socialDetailActivity);
}
