package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.HeyibanFragmentModule;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@FragmentScope
@Subcomponent(modules = HeyibanFragmentModule.class)
public interface HeyibanFragmentComponent {
    //void inject(HeyibanFragment heyibanFragment);
}
