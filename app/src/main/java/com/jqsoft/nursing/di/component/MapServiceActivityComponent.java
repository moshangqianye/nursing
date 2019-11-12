package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.MapServiceActivityModule;

import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@ActivityScope
@Subcomponent(modules = MapServiceActivityModule.class)
public interface MapServiceActivityComponent {
   // void inject(MapServiceActivity activity);
}
