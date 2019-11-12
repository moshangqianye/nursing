package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.SmartAlertActivityModule;
import com.jqsoft.nursing.di.ui.activity.SmartAlertActivity;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@FragmentScope
@Subcomponent(modules = SmartAlertActivityModule.class)
public interface SmartAlertActivityComponent {
    void inject(SmartAlertActivity smartAlertActivity);
}
