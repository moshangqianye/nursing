package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.SubsistenceApprovePovertyReasonStatisticsFragmentModule;
import com.jqsoft.nursing.di.ui.fragment.statistics.SubsistenceApprovePovertyReasonStatisticsFragment;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@FragmentScope
@Subcomponent(modules = SubsistenceApprovePovertyReasonStatisticsFragmentModule.class)
public interface SubsistenceApprovePovertyReasonStatisticsFragmentComponent {
    void inject(SubsistenceApprovePovertyReasonStatisticsFragment fragment);
}
