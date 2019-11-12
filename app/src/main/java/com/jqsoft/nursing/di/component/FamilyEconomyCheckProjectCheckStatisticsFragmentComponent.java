package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.FamilyEconomyCheckProjectCheckStatisticsFragmentModule;
import com.jqsoft.nursing.di.ui.fragment.statistics.FamilyEconomyCheckProjectCheckStatisticsFragment;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Subcomponent;

/**
 * 家庭经济状况核对-核对项目类统计
 * Created by Administrator on 2017/5/21.
 */

@FragmentScope
@Subcomponent(modules = FamilyEconomyCheckProjectCheckStatisticsFragmentModule.class)
public interface FamilyEconomyCheckProjectCheckStatisticsFragmentComponent {
    void inject(FamilyEconomyCheckProjectCheckStatisticsFragment fragment);
}
