package com.jqsoft.nursing.di.component.nursing;

import com.jqsoft.nursing.di.module.nursing.DeanCockpitBedFragmentModule;
import com.jqsoft.nursing.di.ui.fragment.nursing.DeanCockpitBedFragment;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@FragmentScope
@Subcomponent(modules = DeanCockpitBedFragmentModule.class)
public interface DeanCockpitBedFragmentComponent {
    void inject(DeanCockpitBedFragment fragment);
}
