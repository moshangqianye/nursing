package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.MedicalAssistantDirectOutcomeStatisticsFragmentModule;
import com.jqsoft.nursing.di.ui.fragment.statistics.MedicalAssistantDirectOutcomeIncreaseRatioStatisticsFragment;
import com.jqsoft.nursing.di.ui.fragment.statistics.MedicalAssistantDirectOutcomeStatisticsFragment;
import com.jqsoft.nursing.di.ui.fragment.statistics.MedicalAssistantDirectOutcomeTrendStatisticsFragment;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@FragmentScope
@Subcomponent(modules = MedicalAssistantDirectOutcomeStatisticsFragmentModule.class)
public interface MedicalAssistantDirectOutcomeStatisticsFragmentComponent {
    void inject(MedicalAssistantDirectOutcomeStatisticsFragment fragment);
    void inject(MedicalAssistantDirectOutcomeTrendStatisticsFragment fragment);
    void inject(MedicalAssistantDirectOutcomeIncreaseRatioStatisticsFragment fragment);
}
