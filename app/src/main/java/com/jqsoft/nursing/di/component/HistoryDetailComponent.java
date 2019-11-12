package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.HistoryDetailModule;
import com.jqsoft.nursing.di.ui.activity.SocialHistoryPageActivity;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@ActivityScope
@Subcomponent(modules = HistoryDetailModule.class)
public interface HistoryDetailComponent {
    void inject(SocialHistoryPageActivity socialHistoryPageActivity);
}
