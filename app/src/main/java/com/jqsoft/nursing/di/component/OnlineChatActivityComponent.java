package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.OnlineChatingActivityModule;
import com.jqsoft.nursing.di.ui.activity.ChatDetailActivity;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@FragmentScope
@Subcomponent(modules = OnlineChatingActivityModule.class)
public interface OnlineChatActivityComponent {
    void inject(ChatDetailActivity chatDetailActivity);
}
