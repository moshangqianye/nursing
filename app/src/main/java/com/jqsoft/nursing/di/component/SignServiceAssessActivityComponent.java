package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.SignServiceAssessActivityModule;
import com.jqsoft.nursing.di.ui.activity.SignServiceAssessActivity;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@ActivityScope
@Subcomponent(modules = SignServiceAssessActivityModule.class)
public interface SignServiceAssessActivityComponent {
    void inject(SignServiceAssessActivity signServiceAssessActivity);
}
