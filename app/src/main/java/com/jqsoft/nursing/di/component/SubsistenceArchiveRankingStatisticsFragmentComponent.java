package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.SubsistenceArchiveRankingStatisticsFragmentModule;
import com.jqsoft.nursing.di.ui.fragment.statistics.SubsistenceArchiveRankingStatisticsFragment;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@FragmentScope
@Subcomponent(modules = SubsistenceArchiveRankingStatisticsFragmentModule.class)
public interface SubsistenceArchiveRankingStatisticsFragmentComponent {
    void inject(SubsistenceArchiveRankingStatisticsFragment fragment);
}
