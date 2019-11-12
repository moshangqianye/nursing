package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.DeleteFindContract;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/5/21.
 */

@Module
public class DeleteFindFragmentModule {

    private DeleteFindContract.View view;

    public DeleteFindFragmentModule(DeleteFindContract.View view){
        this.view = view;
    }

    @FragmentScope
    @Provides
    public DeleteFindContract.View providerView(){
        return view;
    }

}
