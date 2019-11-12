package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.TempDisasterAssistancePercentageStatisticsFragmentModule;
import com.jqsoft.nursing.di.ui.fragment.statistics.TempDisasterAssistancePercentageStatisticsFragment;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@FragmentScope
@Subcomponent(modules = TempDisasterAssistancePercentageStatisticsFragmentModule.class)
public interface TempDisasterAssistancePercentageStatisticsFragmentComponent {
    void inject(TempDisasterAssistancePercentageStatisticsFragment fragment);
}
