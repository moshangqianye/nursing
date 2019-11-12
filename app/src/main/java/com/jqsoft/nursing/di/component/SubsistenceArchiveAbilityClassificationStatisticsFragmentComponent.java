package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.SubsistenceArchiveAbilityClassificationStatisticsFragmentModule;
import com.jqsoft.nursing.di.ui.fragment.statistics.SubsistenceArchiveAbilityClassificationStatisticsFragment;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@FragmentScope
@Subcomponent(modules = SubsistenceArchiveAbilityClassificationStatisticsFragmentModule.class)
public interface SubsistenceArchiveAbilityClassificationStatisticsFragmentComponent {
    void inject(SubsistenceArchiveAbilityClassificationStatisticsFragment fragment);
}
