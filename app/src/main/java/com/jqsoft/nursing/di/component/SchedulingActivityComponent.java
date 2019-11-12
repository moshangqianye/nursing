package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.SchedulingActivityModule;
import com.jqsoft.nursing.di.module.SchedulingFragmentModule;
import com.jqsoft.nursing.di.ui.activity.SchedulingActivity;
import com.jqsoft.nursing.di.ui.fragment.SchedulingFragment;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Subcomponent;


@ActivityScope
@Subcomponent(modules = SchedulingActivityModule.class)
public interface SchedulingActivityComponent {
    void inject(SchedulingActivity schedulingActivity);
}
