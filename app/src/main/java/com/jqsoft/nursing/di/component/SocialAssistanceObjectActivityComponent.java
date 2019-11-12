package com.jqsoft.nursing.di.component;

import com.jqsoft.nursing.di.module.SocialAssistanceObjectActivityModule;
import com.jqsoft.nursing.di.ui.activity.BuildingRoomActivity;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Subcomponent;


@ActivityScope
@Subcomponent(modules = SocialAssistanceObjectActivityModule.class)
public interface SocialAssistanceObjectActivityComponent {
    void inject(BuildingRoomActivity buildingRoomActivity);
}
