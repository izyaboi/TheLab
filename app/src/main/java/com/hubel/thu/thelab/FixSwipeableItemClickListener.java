package com.hubel.thu.thelab;


import android.content.Context;

import com.hudomju.swipe.OnItemClickListener;
import com.hudomju.swipe.SwipeableItemClickListener;

/**
 * Created by thu on 30.03.16.
 */
public class FixSwipeableItemClickListener extends SwipeableItemClickListener {

    public FixSwipeableItemClickListener(Context context, OnItemClickListener listener) {
        super(context, listener);
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }
}
