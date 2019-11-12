package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.RetrievePasswordModule;
import com.jqsoft.nursing.di.ui.activity.RetrievePasswordActivity;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@ActivityScope
@Subcomponent(modules = RetrievePasswordModule.class)
public interface RetrievePasswordComponent {
    void inject(RetrievePasswordActivity retrievePasswordActivity);
}
