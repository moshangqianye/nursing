package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.SignServiceEvaluteActivityModule;
import com.jqsoft.nursing.di.ui.activity.SignServiceEvalution;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Subcomponent;


@ActivityScope
@Subcomponent(modules = SignServiceEvaluteActivityModule.class)
public interface SignServiceEvalutionComponent {
    void inject(SignServiceEvalution signServiceEvalution);
}
