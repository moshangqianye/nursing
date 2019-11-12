package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.SubsistenceVarianceTrendStatisticsFragmentModule;
import com.jqsoft.nursing.di.ui.fragment.statistics.SubsistenceVarianceTrendStatisticsFragment;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@FragmentScope
@Subcomponent(modules = SubsistenceVarianceTrendStatisticsFragmentModule.class)
public interface SubsistenceVarianceTrendStatisticsFragmentComponent {
    void inject(SubsistenceVarianceTrendStatisticsFragment subsistenceVarianceRankingStatisticsFragment);
}
