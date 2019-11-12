package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.IgGuideActivityModule;
import com.jqsoft.nursing.di.ui.activity.IgGuideActivity;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@ActivityScope
@Subcomponent(modules = IgGuideActivityModule.class)
public interface IgGuideActivityComponent {
    void inject(IgGuideActivity IgGuideActivity);
}
