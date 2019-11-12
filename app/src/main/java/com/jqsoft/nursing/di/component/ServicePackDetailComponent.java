package com.jqsoft.nursing.di.component;


import com.jqsoft.nursing.di.module.ServicePackDetailActivityModule;
import com.jqsoft.nursing.di.ui.activity.DoctorServerDetails;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@ActivityScope
@Subcomponent(modules = ServicePackDetailActivityModule.class)
public interface ServicePackDetailComponent {
    void inject(DoctorServerDetails servicepackdetailActivity);
}
