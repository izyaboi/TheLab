package com.hubel.thu.thelab;

import android.content.Context;
import android.app.Application;
import com.hubel.thu.thelab.injection.component.ApplicationComponent;
import com.hubel.thu.thelab.injection.component.DaggerApplicationComponent;
import com.hubel.thu.thelab.injection.module.ApplicationModule;


/**
 * Created by thu on 04.03.16.
 */
public class LabApplication extends Application {

    ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();


        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))

                .build();
    }

    public static LabApplication get(Context context) {
        return (LabApplication) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }
}
