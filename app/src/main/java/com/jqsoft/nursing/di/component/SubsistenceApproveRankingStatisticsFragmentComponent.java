package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.SubsistenceApproveRankingStatisticsFragmentModule;
import com.jqsoft.nursing.di.ui.fragment.statistics.SubsistenceApproveRankingStatisticsFragment;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@FragmentScope
@Subcomponent(modules = SubsistenceApproveRankingStatisticsFragmentModule.class)
public interface SubsistenceApproveRankingStatisticsFragmentComponent {
    void inject(SubsistenceApproveRankingStatisticsFragment subsistenceApproveRankingStatisticsFragment);
}
