package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.SubsistenceStandardRankingStatisticsFragmentModule;
import com.jqsoft.nursing.di.ui.fragment.statistics.SubsistenceStandardRankingStatisticsFragment;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@FragmentScope
@Subcomponent(modules = SubsistenceStandardRankingStatisticsFragmentModule.class)
public interface SubsistenceStandardRankingStatisticsFragmentComponent {
    void inject(SubsistenceStandardRankingStatisticsFragment fragment);
}
