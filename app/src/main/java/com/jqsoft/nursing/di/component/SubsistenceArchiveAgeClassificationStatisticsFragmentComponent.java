package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.SubsistenceArchiveAgeClassificationStatisticsFragmentModule;
import com.jqsoft.nursing.di.ui.fragment.statistics.SubsistenceArchiveAgeClassificationStatisticsFragment;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@FragmentScope
@Subcomponent(modules = SubsistenceArchiveAgeClassificationStatisticsFragmentModule.class)
public interface SubsistenceArchiveAgeClassificationStatisticsFragmentComponent {
    void inject(SubsistenceArchiveAgeClassificationStatisticsFragment fragment);
}
