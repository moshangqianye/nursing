package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.AddFindModule;
import com.jqsoft.nursing.di.ui.activity.DetailFindDaibanActivity;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@ActivityScope
@Subcomponent(modules = AddFindModule.class)
public interface DetailFindStatusComponent {
    void inject(DetailFindDaibanActivity addFindActivity);
//    void inject(LoginActivity loginActivity);
}
