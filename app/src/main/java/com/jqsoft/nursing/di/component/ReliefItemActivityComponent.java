package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.ReliefItemActivityModule;
import com.jqsoft.nursing.di.ui.activity.ReliefItemActivity;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@ActivityScope
@Subcomponent(modules = ReliefItemActivityModule.class)
public interface ReliefItemActivityComponent {
    void inject(ReliefItemActivity reliefItemActivity);
}
