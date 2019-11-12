package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.HandleFindModule;
import com.jqsoft.nursing.di.ui.activity.HandleFindActivity;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@ActivityScope
@Subcomponent(modules = HandleFindModule.class)
public interface HandleFindComponent {
    void inject(HandleFindActivity addFindActivity);
//    void inject(LoginActivity loginActivity);
}
