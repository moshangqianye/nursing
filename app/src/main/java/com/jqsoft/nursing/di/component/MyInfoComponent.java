package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.MyInfoModule;
import com.jqsoft.nursing.di.ui.activity.MyInfoActivity;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@ActivityScope
@Subcomponent(modules = MyInfoModule.class)
public interface MyInfoComponent {
    void inject(MyInfoActivity myInfoActivity);
}
