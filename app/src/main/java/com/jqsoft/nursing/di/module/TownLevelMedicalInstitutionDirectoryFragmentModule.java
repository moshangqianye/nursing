package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.TownLevelMedicalInstitutionDirectoryFragmentContract;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/5/21.
 */

@Module
public class TownLevelMedicalInstitutionDirectoryFragmentModule {

    private TownLevelMedicalInstitutionDirectoryFragmentContract.View view;

    public TownLevelMedicalInstitutionDirectoryFragmentModule(TownLevelMedicalInstitutionDirectoryFragmentContract.View view){
        this.view = view;
    }

    @FragmentScope
    @Provides
    public TownLevelMedicalInstitutionDirectoryFragmentContract.View providerView(){
        return view;
    }

}
