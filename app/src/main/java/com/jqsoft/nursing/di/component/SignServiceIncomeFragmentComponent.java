package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.SignServiceIncomeFragmentModule;
import com.jqsoft.nursing.di.ui.fragment.SignServiceIncomeFragment;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Subcomponent;


@FragmentScope
@Subcomponent(modules = SignServiceIncomeFragmentModule.class)
public interface SignServiceIncomeFragmentComponent {
    void inject(SignServiceIncomeFragment signServiceIncomeFragment);
}
