package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.IndexFragmentModule;
import com.jqsoft.nursing.di.ui.fragment.IndexFragment;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@FragmentScope
@Subcomponent(modules = IndexFragmentModule.class)
public interface IndexFragmentComponent {
    void inject(IndexFragment indexFragment);
}
