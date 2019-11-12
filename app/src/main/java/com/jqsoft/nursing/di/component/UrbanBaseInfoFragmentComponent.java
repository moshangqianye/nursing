package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.UrbanBaseInfoFragmentModule;
import com.jqsoft.nursing.di.ui.fragment.UrbanBaseInfoFragment;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@FragmentScope
@Subcomponent(modules = UrbanBaseInfoFragmentModule.class)
public interface UrbanBaseInfoFragmentComponent {
    void inject(UrbanBaseInfoFragment myFindFragment);
}
