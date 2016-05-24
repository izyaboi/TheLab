package com.hubel.thu.thelab.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hubel.thu.thelab.R;

/**
 * Created by thu on 24.05.16.
 */

public class MainAdapter extends RecyclerView.Adapter<ViewHolder>
        implements ItemClickListener {

    private Context mContext;

    public MainAdapter(Context context) {
        super();
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}

class ViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {

    TextView mTitleTextView;
    TextView mDescriptionTextView;
    ImageView mImageView;
    ItemClickListener mItemClickListener;

    public ViewHolder(View view, ItemClickListener itemClickListener) {
        super(view);
        mTitleTextView = (TextView) view.findViewById(android.R.id.text1);
        mDescriptionTextView = (TextView) view.findViewById(android.R.id.text2);
        mImageView = (ImageView) view.findViewById(android.R.id.icon);
        mItemClickListener = itemClickListener;
        view.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mItemClickListener.onItemClick(v, getAdapterPosition());
    }
}

interface ItemClickListener {
    void onItemClick(View view, int position);
}