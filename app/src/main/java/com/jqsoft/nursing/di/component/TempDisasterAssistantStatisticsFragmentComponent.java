package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.TempDisasterAssistantStatisticsFragmentModule;
import com.jqsoft.nursing.di.ui.fragment.statistics.TempDisasterAssistantStatisticsFragment;
import com.jqsoft.nursing.di.ui.fragment.statistics.TempDisasterAssistantTrendStatisticsFragment;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@FragmentScope
@Subcomponent(modules = TempDisasterAssistantStatisticsFragmentModule.class)
public interface TempDisasterAssistantStatisticsFragmentComponent {
    void inject(TempDisasterAssistantStatisticsFragment fragment);
    void inject(TempDisasterAssistantTrendStatisticsFragment fragment);
}
