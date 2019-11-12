package com.jqsoft.nursing.di.module.nursing;

import com.jqsoft.nursing.di.contract.nursing.NursingDetailActivityContract;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/5/21.
 */

@Module
public class NursingDetailActivityModule {

    private NursingDetailActivityContract.View view;

    public NursingDetailActivityModule(NursingDetailActivityContract.View view){
        this.view = view;
    }

    @ActivityScope
    @Provides
    public NursingDetailActivityContract.View providerView(){
        return view;
    }

}
