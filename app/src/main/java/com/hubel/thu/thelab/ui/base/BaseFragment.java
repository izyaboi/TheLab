package com.hubel.thu.thelab.ui.base;


import android.support.v4.app.Fragment;
import rx.subscriptions.CompositeSubscription;

/**
 *
 */
public class BaseFragment extends Fragment {

    // region Member Variables
    protected CompositeSubscription compositeSubscription = new CompositeSubscription();
    // endregion

    // region Lifecycle Methods
    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeSubscription.unsubscribe();
    }
    // region
}
