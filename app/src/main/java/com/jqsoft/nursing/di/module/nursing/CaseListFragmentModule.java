package com.jqsoft.nursing.di.module.nursing;

import com.jqsoft.nursing.di.view.ICaseListView;
import com.jqsoft.nursing.di.view.IHealthListView;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * @author yedong
 * @date 2019/1/17
 * 老人病例列表module
 */

@Module
public class CaseListFragmentModule {

    private ICaseListView view;

    public CaseListFragmentModule(ICaseListView view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    public ICaseListView providerView() {
        return view;
    }

}