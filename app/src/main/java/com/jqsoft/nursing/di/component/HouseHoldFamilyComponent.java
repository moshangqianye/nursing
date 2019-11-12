package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.HouseHoldFamilyModule;
import com.jqsoft.nursing.di.ui.fragment.HouseHoldFaimilyFragment;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@ActivityScope
@Subcomponent(modules = HouseHoldFamilyModule.class)
public interface HouseHoldFamilyComponent {
    void inject(HouseHoldFaimilyFragment houseHoldFaimilyFragment);
}
