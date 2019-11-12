package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.NotificationActivityContract;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/5/21.
 */

@Module
public class NotificationActivityModule {

    private NotificationActivityContract.View view;

    public NotificationActivityModule(NotificationActivityContract.View view){
        this.view = view;
    }

    @ActivityScope
    @Provides
    public NotificationActivityContract.View providerView(){
        return view;
    }

}
