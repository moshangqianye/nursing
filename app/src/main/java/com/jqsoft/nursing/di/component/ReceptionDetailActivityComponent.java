package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.ReceptionDetailActivityModule;

import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@ActivityScope
@Subcomponent(modules = ReceptionDetailActivityModule.class)
public interface ReceptionDetailActivityComponent {
   // void inject(ReceptionDetailActivity ReceptionDetailActivity);
}
