package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.InstitutionCharacterClassificationStatisticsFragmentModule;
import com.jqsoft.nursing.di.ui.fragment.statistics.InstitutionCharacterClassificationStatisticsFragment;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@FragmentScope
@Subcomponent(modules = InstitutionCharacterClassificationStatisticsFragmentModule.class)
public interface InstitutionCharacterClassificationStatisticsFragmentComponent {
    void inject( InstitutionCharacterClassificationStatisticsFragment fragment);
}
