package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.ExecutionProjectsActivityModule;
import com.jqsoft.nursing.di.ui.activity.ExecutionProjectsActivity;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@ActivityScope
@Subcomponent(modules = ExecutionProjectsActivityModule.class)
public interface ExecutionProjectsActivityComponent {
    void inject(ExecutionProjectsActivity executionProjectsActivity);
}
