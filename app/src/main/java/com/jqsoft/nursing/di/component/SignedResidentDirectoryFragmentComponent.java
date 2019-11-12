package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.SignedResidentDirectoryFragmentModule;
import com.jqsoft.nursing.di.ui.fragment.SignedResidentDirectoryFragment;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@FragmentScope
@Subcomponent(modules = SignedResidentDirectoryFragmentModule.class)
public interface SignedResidentDirectoryFragmentComponent {
    void inject(SignedResidentDirectoryFragment signedResidentDirectoryFragment);
}
