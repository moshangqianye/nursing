package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.InstitutionLegalPersonClassificationStatisticsFragmentModule;
import com.jqsoft.nursing.di.ui.fragment.statistics.InstitutionLegalPersonClassificationStatisticsFragment;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@FragmentScope
@Subcomponent(modules = InstitutionLegalPersonClassificationStatisticsFragmentModule.class)
public interface InstitutionLegalPersonClassificationStatisticsFragmentComponent {
    void inject(InstitutionLegalPersonClassificationStatisticsFragment fragment);
}
