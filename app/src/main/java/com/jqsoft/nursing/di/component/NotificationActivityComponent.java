package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.NotificationActivityModule;
import com.jqsoft.nursing.di.ui.activity.NotificationActivity;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@ActivityScope
@Subcomponent(modules = NotificationActivityModule.class)
public interface NotificationActivityComponent {
    void inject(NotificationActivity policyActivity);
}
