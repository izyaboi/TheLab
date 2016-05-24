package com.hubel.thu.thelab.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hubel.thu.thelab.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailViewActivityFragment extends Fragment {

    public DetailViewActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_view, container, false);
    }
}
