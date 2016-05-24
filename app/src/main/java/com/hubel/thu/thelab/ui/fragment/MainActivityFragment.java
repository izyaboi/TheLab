package com.hubel.thu.thelab.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.hubel.thu.thelab.R;
import com.hubel.thu.thelab.ui.activity.MainRecyclerView;
import com.hubel.thu.thelab.ui.activity.SignInActivity;
import com.hubel.thu.thelab.ui.adapter.MainAdapter;
import com.hubel.thu.thelab.ui.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends BaseFragment{

    private static final String TAG = "MainActivityFragment";

    @Bind(R.id.recyclerView2)
    MainRecyclerView recyclerView;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private MainAdapter mAdapter;

    public MainActivityFragment() {
    }
    public static MainActivityFragment newInstance(Bundle extras) {
        MainActivityFragment fragment = new MainActivityFragment();
        fragment.setArguments(extras);
        return fragment;
    }

    public static MainActivityFragment newInstance() {
        MainActivityFragment fragment = new MainActivityFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setTitle(getString(R.string.title_activity_main));
        }
        mAdapter = new MainAdapter(getActivity());
        recyclerView.setEmptyView(view.findViewById(android.R.id.empty));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);
    }

    @OnClick(R.id.fab)
    public void clickFab(View view){
        AddItemDialogFragment newFragment = new AddItemDialogFragment();
        newFragment.show(getFragmentManager(),"Add item");
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
