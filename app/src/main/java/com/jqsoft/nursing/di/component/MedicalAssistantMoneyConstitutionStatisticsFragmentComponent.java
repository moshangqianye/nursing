package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.MedicalAssistantMoneyConstitutionStatisticsFragmentModule;
import com.jqsoft.nursing.di.ui.fragment.statistics.MedicalAssistantMoneyConstitutionStatisticsFragment;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@FragmentScope
@Subcomponent(modules = MedicalAssistantMoneyConstitutionStatisticsFragmentModule.class)
public interface MedicalAssistantMoneyConstitutionStatisticsFragmentComponent {
    void inject(MedicalAssistantMoneyConstitutionStatisticsFragment fragment);
}
