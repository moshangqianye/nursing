package com.jqsoft.nursing.di.component.nursing;

import com.jqsoft.nursing.di.module.nursing.DeanCockpitFinanceFragmentModule;
import com.jqsoft.nursing.di.ui.fragment.nursing.DeanCockpitFinanceFragment;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@FragmentScope
@Subcomponent(modules = DeanCockpitFinanceFragmentModule.class)
public interface DeanCockpitFinanceFragmentComponent {
    void inject(DeanCockpitFinanceFragment fragment);
}
