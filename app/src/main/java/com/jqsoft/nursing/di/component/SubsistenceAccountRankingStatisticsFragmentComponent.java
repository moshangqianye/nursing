package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.SubsistenceAccountRankingStatisticsFragmentModule;
import com.jqsoft.nursing.di.ui.fragment.statistics.SubsistenceAccountRankingStatisticsFragment;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@FragmentScope
@Subcomponent(modules = SubsistenceAccountRankingStatisticsFragmentModule.class)
public interface SubsistenceAccountRankingStatisticsFragmentComponent {
    void inject(SubsistenceAccountRankingStatisticsFragment fragment);
}
