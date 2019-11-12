package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.HouseHoldFileModule;
import com.jqsoft.nursing.di.ui.fragment.FileFragment;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@ActivityScope
@Subcomponent(modules = HouseHoldFileModule.class)
public interface HouseHoldFileComponent {
    void inject(FileFragment fileFragment);
}
