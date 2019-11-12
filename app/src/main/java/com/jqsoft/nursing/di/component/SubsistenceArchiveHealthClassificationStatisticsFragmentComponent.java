package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.SubsistenceArchiveHealthClassificationStatisticsFragmentModule;
import com.jqsoft.nursing.di.ui.fragment.statistics.SubsistenceArchiveHealthClassificationStatisticsFragment;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@FragmentScope
@Subcomponent(modules = SubsistenceArchiveHealthClassificationStatisticsFragmentModule.class)
public interface SubsistenceArchiveHealthClassificationStatisticsFragmentComponent {
    void inject(SubsistenceArchiveHealthClassificationStatisticsFragment fragment);
}
