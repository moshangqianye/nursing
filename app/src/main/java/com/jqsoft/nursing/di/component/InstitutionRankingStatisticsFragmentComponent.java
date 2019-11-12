package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.InstitutionRankingStatisticsFragmentModule;
import com.jqsoft.nursing.di.ui.fragment.statistics.InstitutionRankingStatisticsFragment;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@FragmentScope
@Subcomponent(modules = InstitutionRankingStatisticsFragmentModule.class)
public interface InstitutionRankingStatisticsFragmentComponent {
    void inject(InstitutionRankingStatisticsFragment fragment);
}
