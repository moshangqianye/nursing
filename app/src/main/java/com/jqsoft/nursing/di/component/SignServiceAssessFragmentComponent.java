package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.SignServiceAssessFragmentModule;
import com.jqsoft.nursing.di.ui.fragment.SignServiceAssessFragment;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@FragmentScope
@Subcomponent(modules = SignServiceAssessFragmentModule.class)
public interface SignServiceAssessFragmentComponent {
    void inject(SignServiceAssessFragment signServiceAssessFragment);
}
