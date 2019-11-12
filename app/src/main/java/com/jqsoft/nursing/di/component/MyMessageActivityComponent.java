package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.MyMessageActivityModule;
import com.jqsoft.nursing.di.ui.activity.MyMessageActivity;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@ActivityScope
@Subcomponent(modules = MyMessageActivityModule.class)
public interface MyMessageActivityComponent {
    void inject(MyMessageActivity myMessageActivity);
}
