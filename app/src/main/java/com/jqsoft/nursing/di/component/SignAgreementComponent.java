package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.SignAgreementActivityModule;
import com.jqsoft.nursing.di.ui.activity.SignedAgreement;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Subcomponent;


@ActivityScope
@Subcomponent(modules = SignAgreementActivityModule.class)
public interface SignAgreementComponent {
    void inject(SignedAgreement signedAgreement);
}
