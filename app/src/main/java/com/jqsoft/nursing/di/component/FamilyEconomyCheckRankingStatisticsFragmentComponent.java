package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.FamilyEconomyCheckRankingStatisticsFragmentModule;
import com.jqsoft.nursing.di.ui.fragment.statistics.FamilyEconomyCheckRankingStatisticsFragment;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Subcomponent;

/**
 * 家庭经济状况核对-业务受理情况排名统计，核对报告排名统计，核对报告复议排名统计
 * Created by Administrator on 2017/5/21.
 */

@FragmentScope
@Subcomponent(modules = FamilyEconomyCheckRankingStatisticsFragmentModule.class)
public interface FamilyEconomyCheckRankingStatisticsFragmentComponent {
    void inject(FamilyEconomyCheckRankingStatisticsFragment fragment);
}
