package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.SubsistenceApproveTrendStatisticsFragmentModule;
import com.jqsoft.nursing.di.ui.fragment.statistics.SubsistenceApproveTrendStatisticsFragment;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@FragmentScope
@Subcomponent(modules = SubsistenceApproveTrendStatisticsFragmentModule.class)
public interface SubsistenceApproveTrendStatisticsFragmentComponent {
    void inject(SubsistenceApproveTrendStatisticsFragment subsistenceApproveRankingStatisticsFragment);
}
