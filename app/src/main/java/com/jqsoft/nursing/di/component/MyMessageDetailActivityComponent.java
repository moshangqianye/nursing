package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.MyMessageDetailActivityModule;
import com.jqsoft.nursing.di.ui.activity.MyMessageDetailActivity;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@ActivityScope
@Subcomponent(modules = MyMessageDetailActivityModule.class)
public interface MyMessageDetailActivityComponent {
    void inject(MyMessageDetailActivity myMessageDetailActivity);
}
