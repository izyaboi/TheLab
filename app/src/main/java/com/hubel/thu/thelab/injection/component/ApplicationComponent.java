package com.hubel.thu.thelab.injection.component;

import android.app.Application;
import android.content.Context;


import com.hubel.thu.thelab.injection.ApplicationContext;
import com.hubel.thu.thelab.injection.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by thu on 07.04.16.
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    @ApplicationContext
    Context context();
    Application application();
}
