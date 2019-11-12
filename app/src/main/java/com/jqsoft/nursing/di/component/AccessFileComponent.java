package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.AccessFileModule;
import com.jqsoft.nursing.di.ui.activity.AccessFileActivity;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@FragmentScope
@Subcomponent(modules = AccessFileModule.class)
public interface AccessFileComponent {
    void inject(AccessFileActivity accessFileActivity);
}
