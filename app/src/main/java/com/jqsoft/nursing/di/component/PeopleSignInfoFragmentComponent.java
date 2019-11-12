package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.PeopleSignInfoFragmentModule;
import com.jqsoft.nursing.di.ui.fragment.PeopleSignFragment;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@FragmentScope
@Subcomponent(modules = PeopleSignInfoFragmentModule.class)
public interface PeopleSignInfoFragmentComponent {
    void inject(PeopleSignFragment peopleSignInfoFragment);
}
