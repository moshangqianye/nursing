package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.InstitutionServerClassificationStatisticsFragmentModule;
import com.jqsoft.nursing.di.ui.fragment.statistics.InstitutionServerClassificationStatisticsFragment;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@FragmentScope
@Subcomponent(modules = InstitutionServerClassificationStatisticsFragmentModule.class)
public interface InstitutionServerClassificationStatisticsFragmentComponent {
    void inject(InstitutionServerClassificationStatisticsFragment fragment);
}
