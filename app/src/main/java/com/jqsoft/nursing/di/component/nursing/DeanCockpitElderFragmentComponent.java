package com.jqsoft.nursing.di.component.nursing;

import com.jqsoft.nursing.di.module.nursing.DeanCockpitElderFragmentModule;
import com.jqsoft.nursing.di.ui.fragment.nursing.DeanCockpitElderFragment;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@FragmentScope
@Subcomponent(modules = DeanCockpitElderFragmentModule.class)
public interface DeanCockpitElderFragmentComponent {
    void inject(DeanCockpitElderFragment fragment);
}
