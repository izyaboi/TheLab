package com.hubel.thu.thelab.ui.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseRecyclerAdapter;
import com.hubel.thu.thelab.R;

/**
 * Created by thu on 30.03.16.
 */
public class MessageAdapter extends FirebaseRecyclerAdapter<String,MessageAdapter.MessageViewHolder> {
    private ColorGenerator generator = ColorGenerator.MATERIAL;

    public MessageAdapter(Class<String> modelClass, int modelLayout, Class<MessageViewHolder> viewHolderClass, Query ref) {
        super(modelClass, modelLayout, viewHolderClass, ref);
    }

    @Override
    protected void populateViewHolder(MessageViewHolder messageViewHolder, String s, int i) {

        String letter = String.valueOf(s.charAt(0));
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(letter, generator.getRandomColor());
        messageViewHolder.mLetter.setImageDrawable(drawable);
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

