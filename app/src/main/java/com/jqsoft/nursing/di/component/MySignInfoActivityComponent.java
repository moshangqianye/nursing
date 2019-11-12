package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.MySignInfoActivityModule;
import com.jqsoft.nursing.di.ui.activity.MySignInfoActivity;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@ActivityScope
@Subcomponent(modules = MySignInfoActivityModule.class)
public interface MySignInfoActivityComponent {
    void inject(MySignInfoActivity mySignInfoActivity);
}
