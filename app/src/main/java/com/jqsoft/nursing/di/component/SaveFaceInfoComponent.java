package com.jqsoft.nursing.di.component;


import com.jqsoft.nursing.arcface.CardImageLiveFaceVerifyActivity;
import com.jqsoft.nursing.di.module.SaveFaceInfoModule;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by Administrator on 2017/5/21.
 */

@ActivityScope
@Subcomponent(modules = SaveFaceInfoModule.class)
public interface SaveFaceInfoComponent {
    void inject(CardImageLiveFaceVerifyActivity loginActivity);
//    void inject(LoginActivity loginActivity);
}
