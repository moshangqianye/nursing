package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.SubsistenceStandardAverageRankingStatisticsFragmentModule;
import com.jqsoft.nursing.di.ui.fragment.statistics.SubsistenceStandardAverageRankingStatisticsFragment;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@FragmentScope
@Subcomponent(modules = SubsistenceStandardAverageRankingStatisticsFragmentModule.class)
public interface SubsistenceStandardAverageRankingStatisticsFragmentComponent {
    void inject(SubsistenceStandardAverageRankingStatisticsFragment fragment);
}
