package com.jqsoft.nursing.arcface;

import com.jqsoft.nursing.di.contract.ArcFaceListActivityContract;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/5/21.
 */

@Module
public class HeadCollectActivityModule {

    private HeadCollectActivityContract.View view;

    public HeadCollectActivityModule(HeadCollectActivityContract.View  view){
        this.view = view;
    }

    @ActivityScope
    @Provides
    public HeadCollectActivityContract.View providerView(){
        return view;
    }

}
