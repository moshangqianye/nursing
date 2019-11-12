package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.TownLevelMedicalInstitutionDirectoryFragmentModule;
import com.jqsoft.nursing.di.ui.fragment.TownLevelMedicalInstitutionDirectoryFragment;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@FragmentScope
@Subcomponent(modules = TownLevelMedicalInstitutionDirectoryFragmentModule.class)
public interface TownLevelMedicalInstitutionDirectoryFragmentComponent {
    void inject(TownLevelMedicalInstitutionDirectoryFragment townLevelMedicalInstitutionDirectoryFragment);
}
