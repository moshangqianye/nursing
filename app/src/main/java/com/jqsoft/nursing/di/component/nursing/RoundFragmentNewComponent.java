package com.jqsoft.nursing.di.component.nursing;

import com.jqsoft.nursing.di.module.RoundRoomFramentModule;
import com.jqsoft.nursing.di.ui.activity.WorkbenchActivity;
import com.jqsoft.nursing.di.ui.fragment.NursingFragment;
import com.jqsoft.nursing.di.ui.fragment.RoundFragment;
import com.jqsoft.nursing.di.ui.fragment.nursing.RoundFragmentNew;
import com.jqsoft.nursing.di_app.ActivityScope;

import dagger.Subcomponent;


@ActivityScope
@Subcomponent(modules = RoundRoomFramentModule.class)
public interface RoundFragmentNewComponent {
    void inject(RoundFragment buildingRoomActivity);
    void inject(NursingFragment nursingFragment);
    void inject(WorkbenchActivity workbenchActivity);
    void inject(RoundFragmentNew workbenchActivity);
}
