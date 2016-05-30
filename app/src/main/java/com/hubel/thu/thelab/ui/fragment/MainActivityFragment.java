package com.hubel.thu.thelab.ui.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.hubel.thu.thelab.R;
import com.hubel.thu.thelab.ui.activity.MainRecyclerView;
import com.hubel.thu.thelab.ui.adapter.MainAdapter;
import com.hubel.thu.thelab.ui.base.BaseFragment;
import com.hubel.thu.thelab.ui.transitions.FabTransform;
import com.hubel.thu.thelab.ui.util.AnimUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.hubel.thu.thelab.R.id.fab;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends BaseFragment{

    private static final String TAG = "MainActivityFragment";

    private static final int RC_ADD_ITEM = 1;

    @BindView(R.id.recyclerView2)
    MainRecyclerView recyclerView;

    @BindView(R.id.toolbar)
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
        if (savedInstanceState == null) {
            animateToolbar();
        }

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

    @OnClick(fab)
    public void clickFab(View view){


  /*      Intent intent = new Intent(getActivity(), AddItemDialogFragment.class);
        FabTransform.addExtras(intent,
                ContextCompat.getColor(getActivity(), R.color.colorAccent), R.drawable.ic_add_dark);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), fab,
                getString(R.string.transition_add_item));
        startActivityForResult(intent, RC_ADD_ITEM, options.toBundle());*/
    }


    private void animateToolbar() {
        // this is gross but toolbar doesn't expose it's children to animate them :(
        View t = toolbar.getChildAt(0);
        if (t != null && t instanceof TextView) {
            TextView title = (TextView) t;

            // fade in and space out the title.  Animating the letterSpacing performs horribly so
            // fake it by setting the desired letterSpacing then animating the scaleX ¯\_(ツ)_/¯
            title.setAlpha(0f);
            title.setScaleX(0.8f);

            title.animate()
                    .alpha(1f)
                    .scaleX(1f)
                    .setStartDelay(300)
                    .setDuration(900)
                    .setInterpolator(AnimUtils.getFastOutSlowInInterpolator(getActivity()));
        }
        View amv = toolbar.getChildAt(1);
        if (amv != null & amv instanceof ActionMenuView) {
            ActionMenuView actions = (ActionMenuView) amv;
            popAnim(actions.getChildAt(0), 500, 200); // filter
            popAnim(actions.getChildAt(1), 700, 200); // overflow
        }
    }

    private void popAnim(View v, int startDelay, int duration) {
        if (v != null) {
            v.setAlpha(0f);
            v.setScaleX(0f);
            v.setScaleY(0f);

            v.animate()
                    .alpha(1f)
                    .scaleX(1f)
                    .scaleY(1f)
                    .setStartDelay(startDelay)
                    .setDuration(duration)
                    .setInterpolator(AnimationUtils.loadInterpolator(getActivity(),
                            android.R.interpolator.overshoot));
        }
    }


}
