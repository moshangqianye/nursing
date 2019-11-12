package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.MedicalAssistantFinanceAssuranceStatisticsFragmentModule;
import com.jqsoft.nursing.di.ui.fragment.statistics.MedicalAssistantFinanceAssuranceIncreaseRatioFragment;
import com.jqsoft.nursing.di.ui.fragment.statistics.MedicalAssistantFinanceAssuranceStatisticsFragment;
import com.jqsoft.nursing.di.ui.fragment.statistics.MedicalAssistantFinanceAssuranceTrendStatisticsFragment;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@FragmentScope
@Subcomponent(modules = MedicalAssistantFinanceAssuranceStatisticsFragmentModule.class)
public interface MedicalAssistantFinanceAssuranceStatisticsFragmentComponent {
    void inject(MedicalAssistantFinanceAssuranceStatisticsFragment fragment);
    void inject(MedicalAssistantFinanceAssuranceTrendStatisticsFragment fragment);
    void inject(MedicalAssistantFinanceAssuranceIncreaseRatioFragment fragment);
}
