package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.SignClientServiceAssessActivityModule;
import com.jqsoft.nursing.di.ui.activity.ClientSignServiceAssessActivity;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@ActivityScope
@Subcomponent(modules = SignClientServiceAssessActivityModule.class)
public interface SignClientServiceAssessActivityComponent {
    void inject(ClientSignServiceAssessActivity clientSignServiceAssessActivity);
}
