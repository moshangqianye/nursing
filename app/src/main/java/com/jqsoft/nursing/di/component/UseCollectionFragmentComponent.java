package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.UseCollectionFragmentModule;
import com.jqsoft.nursing.di.ui.fragment.SimpleCardFragment;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Subcomponent;


@ActivityScope
@Subcomponent(modules = UseCollectionFragmentModule.class)
public interface UseCollectionFragmentComponent {
    void inject(SimpleCardFragment simpleCardFragment);
}
