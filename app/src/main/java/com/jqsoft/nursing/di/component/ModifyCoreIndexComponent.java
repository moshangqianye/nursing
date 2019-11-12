package com.jqsoft.nursing.di.component;


import com.jqsoft.nursing.di.module.CoreIndexActivityModule;
import com.jqsoft.nursing.di.ui.activity.MotifyExecuActivity;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@ActivityScope
@Subcomponent(modules = CoreIndexActivityModule.class)
public interface ModifyCoreIndexComponent {
    void inject(MotifyExecuActivity coreIndexActivity);
}
