package com.jqsoft.nursing.di.component;


import com.jqsoft.nursing.di.module.ReserverActivityModule;
import com.jqsoft.nursing.di.ui.activity.ReserrverServerActivity;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@ActivityScope
@Subcomponent(modules = ReserverActivityModule.class)
public interface ReserverComponent {
    void inject(ReserrverServerActivity coreIndexActivity);
}
