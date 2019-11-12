package com.jqsoft.nursing.di.component.nursing;

import com.jqsoft.nursing.di.module.nursing.SpecificNursingFragmentModule;
import com.jqsoft.nursing.di.ui.fragment.nursing.SpecificNursingFragment;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@FragmentScope
@Subcomponent(modules = SpecificNursingFragmentModule.class)
public interface SpecificNursingFragmentComponent {
    void inject(SpecificNursingFragment fragment);
}
