package com.jqsoft.nursing.arcface;

import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@ActivityScope
@Subcomponent(modules = HeadCollectActivityModule.class)
public interface HeadCollectActivityComponent {
    void inject(HeadCollectActivity activity);
}
