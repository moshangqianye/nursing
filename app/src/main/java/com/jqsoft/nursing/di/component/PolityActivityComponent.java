package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.PolityActivityModule;

import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@ActivityScope
@Subcomponent(modules = PolityActivityModule.class)
public interface PolityActivityComponent {
    //void inject(PoliticsActivity polityActivity);
}
