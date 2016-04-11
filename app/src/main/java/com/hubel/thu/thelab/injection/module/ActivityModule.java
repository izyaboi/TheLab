package com.hubel.thu.thelab.injection.module;

import android.app.Activity;
import android.content.Context;

import com.hubel.thu.thelab.injection.ActivityContext;

import dagger.Module;
import dagger.Provides;

/**
 * Created by thu on 07.04.16.
 */
@Module
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    Activity provideActivity() {
        return mActivity;
    }

    @Provides
    @ActivityContext
    Context providesContext() {
        return mActivity;
    }

}
