package com.jqsoft.nursing.di.module;


import com.jqsoft.nursing.di.contract.SaveFaceInfoContract;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/5/21.
 */

@Module
public class SaveFaceInfoModule {

    private SaveFaceInfoContract.View view;

    public SaveFaceInfoModule(SaveFaceInfoContract.View view){
        this.view = view;
    }

    @ActivityScope
    @Provides
    public SaveFaceInfoContract.View providerView(){
        return view;
    }

}