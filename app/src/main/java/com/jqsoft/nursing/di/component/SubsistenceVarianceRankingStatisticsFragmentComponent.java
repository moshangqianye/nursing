package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.SubsistenceVarianceRankingStatisticsFragmentModule;
import com.jqsoft.nursing.di.ui.fragment.statistics.SubsistenceVarianceRankingStatisticsFragment;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@FragmentScope
@Subcomponent(modules = SubsistenceVarianceRankingStatisticsFragmentModule.class)
public interface SubsistenceVarianceRankingStatisticsFragmentComponent {
    void inject(SubsistenceVarianceRankingStatisticsFragment subsistenceVarianceRankingStatisticsFragment);
}
