package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.ReceptionDetailNewListActivityModule;
import com.jqsoft.nursing.di.ui.activity.ReceptionDetailNewListActivity;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@ActivityScope
@Subcomponent(modules = ReceptionDetailNewListActivityModule.class)
public interface ReceptionDetailNewListActivityComponent {
    void inject(ReceptionDetailNewListActivity receptionDetailNewListActivity);
}
