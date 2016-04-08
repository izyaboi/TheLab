package com.hubel.thu.thelab.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseRecyclerAdapter;
import com.hubel.thu.thelab.FixSwipeableItemClickListener;
import com.hubel.thu.thelab.R;
import com.hubel.thu.thelab.Settings;
import com.hubel.thu.thelab.ui.base.BaseActivity;
import com.hudomju.swipe.OnItemClickListener;
import com.hudomju.swipe.SwipeToDismissTouchListener;
import com.hudomju.swipe.SwipeableItemClickListener;
import com.hudomju.swipe.adapter.RecyclerViewAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.coordinator)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.gmail_fab)
    FloatingActionButton fab;

    FirebaseRecyclerAdapter<String, MessageViewHolder> mAdapter ;

    Firebase mFirebaseRef = new Firebase(Settings.FIREBASE_URL);
    private boolean showFab = true;
    ColorGenerator generator = ColorGenerator.MATERIAL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activityComponent().inject(this);
        setSupportActionBar(mToolbar);

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(R.string.app_name);
        }

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        final SwipeToDismissTouchListener<RecyclerViewAdapter> touchListener = new SwipeToDismissTouchListener<>(
                new RecyclerViewAdapter(mRecyclerView),
                new SwipeToDismissTouchListener.DismissCallbacks<RecyclerViewAdapter>(){
            @Override
            public void onDismiss(RecyclerViewAdapter view ,int position) {
                mAdapter.notifyItemRemoved(position);
            }

            @Override
            public boolean canDismiss(int position) {
                return true;
            }
        });

        mRecyclerView.setOnTouchListener(touchListener);
        mRecyclerView.addOnScrollListener((RecyclerView.OnScrollListener)touchListener.makeScrollListener());
        mRecyclerView.addOnItemTouchListener(new FixSwipeableItemClickListener(this,
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (view.getId() == R.id.txt_delete) {
                            touchListener.processPendingDismisses();
                        } else if (view.getId() == R.id.txt_undo) {
                            touchListener.undoPendingDismiss();
                        } else { // R.id.txt_data
                            Toast.makeText(getApplicationContext(), "Position " + position, Toast.LENGTH_SHORT).show();
                        }
                    }
                }));

        final Animation growAnimation = AnimationUtils.loadAnimation(this, R.anim.simple_grow);
        final Animation shrinkAnimation = AnimationUtils.loadAnimation(this, R.anim.simple_shrink);

        fab.setVisibility(View.VISIBLE);
        fab.startAnimation(growAnimation);

        shrinkAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                fab.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        View bottomSheet = coordinatorLayout.findViewById(R.id.design_bottom_sheet);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);

        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState){
                    case BottomSheetBehavior.STATE_DRAGGING:
                        if(showFab){
                            fab.startAnimation(shrinkAnimation);
                        }
                        break;


                    case BottomSheetBehavior.STATE_COLLAPSED:
                        showFab = true;
                        fab.setVisibility(View.VISIBLE);
                        fab.startAnimation(growAnimation);
                        break;

                    case BottomSheetBehavior.STATE_EXPANDED:
                        showFab = false;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

    }

/*
    @OnClick(R.id.fab)
    public void clickFab(View view){
        AddItemDialogFragment newFragment = new AddItemDialogFragment();
        newFragment.show(getSupportFragmentManager(),"Add item");
    }
*/



    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter.cleanup();
    }


    @Override
    protected void onStart() {
        super.onStart();

        mAdapter = new FirebaseRecyclerAdapter<String, MessageViewHolder>(
                String.class,
                R.layout.item_layout,
                MessageViewHolder.class,
                mFirebaseRef
        ) {
            @Override
            protected void populateViewHolder(MessageViewHolder messageViewHolder, String s, int i) {

                String letter = String.valueOf(s.charAt(0));
                TextDrawable drawable = TextDrawable.builder()
                        .buildRound(letter, generator.getRandomColor());
                messageViewHolder.mLetter.setImageDrawable(drawable);
                messageViewHolder.mText.setText(s);
            }
        };

        mRecyclerView.setAdapter(mAdapter);
    }


    public static class MessageViewHolder extends RecyclerView.ViewHolder{
        TextView mText;
        ImageView mLetter;

        MessageViewHolder(View view) {
            super(view);
            mText = (TextView) view.findViewById(R.id.txt_data);
            mLetter = (ImageView) view.findViewById(R.id.txt_letter);
        }
    }

    public Firebase getmFirebaseRef() {
        return mFirebaseRef;
    }



}
