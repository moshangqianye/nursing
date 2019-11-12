package com.jqsoft.nursing.di.module.nursing;

import com.jqsoft.nursing.di.view.IHealthListView;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * @author yedong
 * @date 2019/1/17
 * 老人健康列表module
 */

@Module
public class HealthListFragmentModule {

    private IHealthListView view;

    public HealthListFragmentModule(IHealthListView view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    public IHealthListView providerView() {
        return view;
    }

}