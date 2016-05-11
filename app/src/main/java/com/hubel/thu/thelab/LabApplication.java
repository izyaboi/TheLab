package com.hubel.thu.thelab;

import android.content.Context;
import android.app.Application;
import com.firebase.client.Firebase;
import com.hubel.thu.thelab.injection.component.ApplicationComponent;
import com.hubel.thu.thelab.injection.component.DaggerApplicationComponent;
import com.hubel.thu.thelab.injection.module.ApplicationModule;
import com.stormpath.sdk.Stormpath;
import com.stormpath.sdk.StormpathConfiguration;

/**
 * Created by thu on 04.03.16.
 */
public class LabApplication extends Application {

    ApplicationComponent mApplicationComponent;

    private final String BASE_URL = "https://api.stormpath.com/v1/applications/7LTpvQ5lSTvFqJ02qQVpPF";

    @Override
    public void onCreate() {
        super.onCreate();

        Firebase.setAndroidContext(this);
        StormpathConfiguration stormpathConfiguration = new StormpathConfiguration.Builder()
                .baseUrl(BASE_URL)
                .build();
        Stormpath.init(this,stormpathConfiguration);

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
