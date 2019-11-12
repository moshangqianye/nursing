package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.SRCLoginModule;
import com.jqsoft.nursing.di.ui.activity.IntroductionActivity;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@ActivityScope
@Subcomponent(modules = SRCLoginModule.class)
public interface IntroductionComponent {
    void inject(IntroductionActivity loginActivity);
//    void inject(LoginActivity loginActivity);
}
