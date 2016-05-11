package com.hubel.thu.thelab.injection.component;

import com.hubel.thu.thelab.ui.activity.MainActivity;
import com.hubel.thu.thelab.injection.PerActivity;
import com.hubel.thu.thelab.injection.module.ActivityModule;
import com.hubel.thu.thelab.ui.activity.SignInActivity;

import dagger.Component;

/**
 * Created by thu on 07.04.16.
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class , modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(SignInActivity signInActivity);
    void inject(MainActivity mainActivity);

}
