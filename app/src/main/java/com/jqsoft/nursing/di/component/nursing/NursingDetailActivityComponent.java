package com.jqsoft.nursing.di.component.nursing;

import com.jqsoft.nursing.di.module.nursing.NursingDetailActivityModule;
import com.jqsoft.nursing.di.ui.activity.nursing.NursingDetailActivity;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@ActivityScope
@Subcomponent(modules = NursingDetailActivityModule.class)
public interface NursingDetailActivityComponent {
    void inject(NursingDetailActivity activity);
}
