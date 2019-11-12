package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.HouseHoldserveryModule;
import com.jqsoft.nursing.di.ui.fragment.HouseHoldServeyFragment;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@ActivityScope
@Subcomponent(modules = HouseHoldserveryModule.class)
public interface HouseHoldServeryComponent {
    void inject(HouseHoldServeyFragment houseHoldServeyFragment);
}
