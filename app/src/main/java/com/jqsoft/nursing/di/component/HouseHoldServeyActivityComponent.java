package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.HouseHoldServeyActivityModule;
import com.jqsoft.nursing.di.ui.activity.HouseholdSurveysActivity;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@ActivityScope
@Subcomponent(modules = HouseHoldServeyActivityModule.class)
public interface HouseHoldServeyActivityComponent {
    void inject(HouseholdSurveysActivity householdSurveysActivity);
}
