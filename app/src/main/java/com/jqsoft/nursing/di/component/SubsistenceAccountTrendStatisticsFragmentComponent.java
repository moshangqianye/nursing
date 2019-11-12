package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.SubsistenceAccountTrendStatisticsFragmentModule;
import com.jqsoft.nursing.di.ui.fragment.statistics.SubsistenceAccountTrendStatisticsFragment;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@FragmentScope
@Subcomponent(modules = SubsistenceAccountTrendStatisticsFragmentModule.class)
public interface SubsistenceAccountTrendStatisticsFragmentComponent {
    void inject(SubsistenceAccountTrendStatisticsFragment fragment);
}
