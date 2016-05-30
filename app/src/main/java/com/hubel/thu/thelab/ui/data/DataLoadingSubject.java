package com.hubel.thu.thelab.ui.data;

/**
 * Created by thu on 25.05.16.
 * An interface for classes offering data loading state to be observed.
 */

public interface DataLoadingSubject {
    boolean isDataLoading();
    void registerCallback(DataLoadingCallbacks callbacks);
    void unregisterCallback(DataLoadingCallbacks callbacks);

    interface DataLoadingCallbacks {
        void dataStartedLoading();
        void dataFinishedLoading();

    }
}

