package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.OnlineChatingFragmentModule;
import com.jqsoft.nursing.di.ui.fragment.OnlineChatingFragment;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@FragmentScope
@Subcomponent(modules = OnlineChatingFragmentModule.class)
public interface OnlineChatFragmentComponent {
    void inject(OnlineChatingFragment indexFragment);
}
