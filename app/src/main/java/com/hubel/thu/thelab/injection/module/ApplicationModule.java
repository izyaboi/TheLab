package com.hubel.thu.thelab.injection.module;

import android.app.Application;
import android.content.Context;

import com.hubel.thu.thelab.injection.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by thu on 07.04.16.
 * Provide application-level dependencies. Mainly singleton object that can be injected from
 * anywhere in the app.
 */

@Module
public class ApplicationModule {
    protected final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }
}
