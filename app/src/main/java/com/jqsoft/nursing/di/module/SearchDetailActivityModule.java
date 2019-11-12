package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.SearchDetailContract;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/5/21.
 */

@Module
public class SearchDetailActivityModule {

    private SearchDetailContract.View view;

    public SearchDetailActivityModule(SearchDetailContract.View view){
        this.view = view;
    }

    @ActivityScope
    @Provides
    public SearchDetailContract.View providerView(){
        return view;
    }

}
