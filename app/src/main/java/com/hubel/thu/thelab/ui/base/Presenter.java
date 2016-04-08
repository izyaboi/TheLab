package com.hubel.thu.thelab.ui.base;

/**
 * Created by thu on 08.04.16.
 *
 * Every presenter in the app must either implement this interface or extend BasePresenter
 * indicating the MvpView type that wants to be attached with.
 */
public interface Presenter<V extends MVPView> {

    void attachView(V mvpView);
    void detachView();
}
