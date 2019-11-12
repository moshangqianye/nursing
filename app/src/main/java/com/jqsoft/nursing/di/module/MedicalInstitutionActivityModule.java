package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.MedicalInstitutionActivityContract;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/5/21.
 */

@Module
public class MedicalInstitutionActivityModule {

    private MedicalInstitutionActivityContract.View view;

    public MedicalInstitutionActivityModule(MedicalInstitutionActivityContract.View view){
        this.view = view;
    }

    @ActivityScope
    @Provides
    public MedicalInstitutionActivityContract.View providerView(){
        return view;
    }

}
