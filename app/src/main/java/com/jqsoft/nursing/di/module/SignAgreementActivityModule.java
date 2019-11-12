package com.jqsoft.nursing.di.module;

import com.jqsoft.nursing.di.contract.SignedAgreementContract;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/5/21.
 */

@Module
public class SignAgreementActivityModule {

    private SignedAgreementContract.View view;

    public SignAgreementActivityModule(SignedAgreementContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    public SignedAgreementContract.View providerView() {
        return view;
    }

}
