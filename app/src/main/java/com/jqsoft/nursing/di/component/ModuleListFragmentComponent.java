package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.ModuleListFragmentModule;
import com.jqsoft.nursing.di.ui.fragment.QueryDataFragment;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Subcomponent;


@FragmentScope
@Subcomponent(modules = ModuleListFragmentModule.class)
public interface ModuleListFragmentComponent {
    void inject(QueryDataFragment queryDataFragment);
}
