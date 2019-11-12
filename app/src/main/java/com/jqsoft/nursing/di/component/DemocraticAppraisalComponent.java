package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.DemocraticAppraisalModule;
import com.jqsoft.nursing.di.ui.fragment.DemocraticAppraisalfragment;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@ActivityScope
@Subcomponent(modules = DemocraticAppraisalModule.class)
public interface DemocraticAppraisalComponent {
    void inject(DemocraticAppraisalfragment democraticAppraisalfragment);
}
