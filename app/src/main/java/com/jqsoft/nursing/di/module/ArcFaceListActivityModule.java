package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.ArcFaceListActivityContract;
import com.jqsoft.nursing.di.contract.MedicalInstitutionActivityContract;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/5/21.
 */

@Module
public class ArcFaceListActivityModule {

    private ArcFaceListActivityContract.View view;

    public ArcFaceListActivityModule(ArcFaceListActivityContract.View view){
        this.view = view;
    }

    @ActivityScope
    @Provides
    public ArcFaceListActivityContract.View providerView(){
        return view;
    }

}
