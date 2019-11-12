package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.SaveFamilyDoctorSignContract;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/5/21.
 */

@Module
public class SaveFamilyDoctorSignFragmentModule {

    private SaveFamilyDoctorSignContract.View view;

    public SaveFamilyDoctorSignFragmentModule(SaveFamilyDoctorSignContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    public SaveFamilyDoctorSignContract.View providerView() {
        return view;
    }

}
