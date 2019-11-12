package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.GuideActivityModule;
import com.jqsoft.nursing.di.ui.activity.GuideActivity;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@ActivityScope
@Subcomponent(modules = GuideActivityModule.class)
public interface GuideActivityComponent {
    void inject(GuideActivity GuideActivity);
}
