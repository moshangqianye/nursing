package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.QuestionActivityContract;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Module;
import dagger.Provides;



@Module
public class QuestionActivityModule {

    private QuestionActivityContract.View view;

    public QuestionActivityModule(QuestionActivityContract.View view){
        this.view = view;
    }

    @ActivityScope
    @Provides
    public QuestionActivityContract.View providerView(){
        return view;
    }

}
