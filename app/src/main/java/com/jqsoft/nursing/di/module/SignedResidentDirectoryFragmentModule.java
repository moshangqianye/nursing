package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.SignedResidentDirectoryFragmentContract;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/5/21.
 */

@Module
public class SignedResidentDirectoryFragmentModule {

    private SignedResidentDirectoryFragmentContract.View view;

    public SignedResidentDirectoryFragmentModule(SignedResidentDirectoryFragmentContract.View view){
        this.view = view;
    }

    @FragmentScope
    @Provides
    public SignedResidentDirectoryFragmentContract.View providerView(){
        return view;
    }

}
