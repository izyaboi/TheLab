package com.hubel.thu.thelab.ui.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.Query;
import com.firebase.ui.FirebaseRecyclerAdapter;
import com.hubel.thu.thelab.R;

/**
 * Created by thu on 30.03.16.
 */
public class MesssageAdapter extends FirebaseRecyclerAdapter<String,MesssageAdapter.MessageViewHolder> {


    public MesssageAdapter(Class<String> modelClass, int modelLayout, Class<MessageViewHolder> viewHolderClass, Query ref) {
        super(modelClass, modelLayout, viewHolderClass, ref);
    }

    @Override
    protected void populateViewHolder(MessageViewHolder messageViewHolder, String s, int i) {
        messageViewHolder.mText.setText(s);
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

}

