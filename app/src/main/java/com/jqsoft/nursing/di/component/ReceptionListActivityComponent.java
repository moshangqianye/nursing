package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.ReceptionListActivityModule;
import com.jqsoft.nursing.di.ui.activity.ReceptionListActivity;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@ActivityScope
@Subcomponent(modules = ReceptionListActivityModule.class)
public interface ReceptionListActivityComponent {
    void inject(ReceptionListActivity ReceptionListActivity);
}
