package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.SubsistenceAccountIncreaseRatioStatisticsFragmentModule;
import com.jqsoft.nursing.di.ui.fragment.statistics.SubsistenceAccountIncreaseRatioStatisticsFragment;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@FragmentScope
@Subcomponent(modules = SubsistenceAccountIncreaseRatioStatisticsFragmentModule.class)
public interface SubsistenceAccountIncreaseRatioStatisticsFragmentComponent {
    void inject(SubsistenceAccountIncreaseRatioStatisticsFragment fragment);
}
