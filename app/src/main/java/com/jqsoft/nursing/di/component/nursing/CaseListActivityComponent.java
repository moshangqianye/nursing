package com.jqsoft.nursing.di.component.nursing;

import com.jqsoft.nursing.di.module.nursing.CaseListFragmentModule;
import com.jqsoft.nursing.di.ui.fragment.CaseListFragment;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@FragmentScope
@Subcomponent(modules = CaseListFragmentModule.class)
public interface CaseListActivityComponent {
    void inject(CaseListFragment fragment);
}
