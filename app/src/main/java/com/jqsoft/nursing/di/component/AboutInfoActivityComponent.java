package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.AboutInfoActivityModule;
import com.jqsoft.nursing.di.ui.activity.AboutInfoActivity;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@ActivityScope
@Subcomponent(modules = AboutInfoActivityModule.class)
public interface AboutInfoActivityComponent {
    void inject(AboutInfoActivity aboutActivity);
}
