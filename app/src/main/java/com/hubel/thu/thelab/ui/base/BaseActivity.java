package com.hubel.thu.thelab.ui.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.hubel.thu.thelab.LabApplication;
import com.hubel.thu.thelab.injection.component.ActivityComponent;

import com.hubel.thu.thelab.injection.component.DaggerActivityComponent;
import com.hubel.thu.thelab.injection.module.ActivityModule;

import butterknife.ButterKnife;

/**
 * Created by thu on 07.04.16.
 */
public class BaseActivity extends AppCompatActivity {

    private ActivityComponent mActivityComponent;

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public ActivityComponent activityComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = DaggerActivityComponent.builder()
                    .applicationComponent(LabApplication.get(this).getComponent())
                    .build();
        }
        return mActivityComponent;
    }

}
