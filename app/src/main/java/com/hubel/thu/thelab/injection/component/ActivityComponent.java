package com.hubel.thu.thelab.injection.component;

import com.hubel.thu.thelab.ui.main.MainActivity;
import com.hubel.thu.thelab.injection.PerActivity;
import com.hubel.thu.thelab.injection.module.ActivityModule;

import dagger.Component;

/**
 * Created by thu on 07.04.16.
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class , modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);
}
