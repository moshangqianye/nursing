package com.jqsoft.nursing.di.component;


import com.jqsoft.nursing.di.module.PendExecuActivityModule;
import com.jqsoft.nursing.di.ui.activity.PendExecuActivity;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@ActivityScope
@Subcomponent(modules = PendExecuActivityModule.class)
public interface PendExecuComponent {
    void inject(PendExecuActivity pendexecuActivity);
}
