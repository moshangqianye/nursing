package com.jqsoft.nursing.di.component.nursing;

import com.jqsoft.nursing.di.module.nursing.HealthListFragmentModule;
import com.jqsoft.nursing.di.module.nursing.SpecificNursingFragmentModule;
import com.jqsoft.nursing.di.ui.fragment.HealthListFragment;
import com.jqsoft.nursing.di.ui.fragment.nursing.SpecificNursingFragment;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@FragmentScope
@Subcomponent(modules = HealthListFragmentModule.class)
public interface HealthListFragmentComponent {
    void inject(HealthListFragment fragment);
}
