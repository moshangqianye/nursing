package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.HouseHoldServeyBaseModule;
import com.jqsoft.nursing.di.ui.fragment.HouseHoldBaseInfoFragment;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@ActivityScope
@Subcomponent(modules = HouseHoldServeyBaseModule.class)
public interface HouseHoldServeyBaseComponent {
    void inject(HouseHoldBaseInfoFragment houseHoldBaseInfoFragment);
}
