package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.InHospitalInspectFragmentContract;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/5/21.
 */

@Module
public class InHospitalInspectFragmentModule {

    private InHospitalInspectFragmentContract.View view;

    public InHospitalInspectFragmentModule(InHospitalInspectFragmentContract.View view){
        this.view = view;
    }

    @FragmentScope
    @Provides
    public InHospitalInspectFragmentContract.View providerView(){
        return view;
    }

}
