package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.SubsistenceArchiveTrendStatisticsFragmentModule;
import com.jqsoft.nursing.di.ui.fragment.statistics.SubsistenceArchiveTrendStatisticsFragment;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@FragmentScope
@Subcomponent(modules = SubsistenceArchiveTrendStatisticsFragmentModule.class)
public interface SubsistenceArchiveTrendStatisticsFragmentComponent {
    void inject(SubsistenceArchiveTrendStatisticsFragment fragment);
}
