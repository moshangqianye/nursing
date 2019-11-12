package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.DeleteFindFragmentModule;
import com.jqsoft.nursing.di.ui.fragment.MyFindFragment;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@FragmentScope
@Subcomponent(modules = DeleteFindFragmentModule.class)
public interface DeleteFindFragmentComponent {
    void inject(MyFindFragment myFindFragment);
}
