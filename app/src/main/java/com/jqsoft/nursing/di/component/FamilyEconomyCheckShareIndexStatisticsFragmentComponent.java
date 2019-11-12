package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.FamilyEconomyCheckShareIndexStatisticsFragmentModule;
import com.jqsoft.nursing.di.ui.fragment.statistics.FamilyEconomyCheckShareIndexStatisticsFragment;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Subcomponent;

/**
 * 家庭经济状况核对-信息共享指标统计
 * Created by Administrator on 2017/5/21.
 */

@FragmentScope
@Subcomponent(modules = FamilyEconomyCheckShareIndexStatisticsFragmentModule.class)
public interface FamilyEconomyCheckShareIndexStatisticsFragmentComponent {
    void inject(FamilyEconomyCheckShareIndexStatisticsFragment fragment);
}
