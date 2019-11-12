package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.HeyibanFragmentContract;
import com.jqsoft.nursing.di_app.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/5/21.
 */

@Module
public class HeyibanFragmentModule {

    private HeyibanFragmentContract.View view;

    public HeyibanFragmentModule(HeyibanFragmentContract.View view){
        this.view = view;
    }

    @FragmentScope
    @Provides
    public HeyibanFragmentContract.View providerView(){
        return view;
    }

}
