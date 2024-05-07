package com.o7services.colourworld.adaptors;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.o7services.colourworld.ExtdetailActivity;
import com.o7services.colourworld.ExteriorActivity;
import com.o7services.colourworld.R;
import com.o7services.colourworld.customclass.ExtrcatContent;

import com.squareup.picasso.Picasso;

import java.util.List;


public class ExtrcatAdapter extends RecyclerView.Adapter<ExtrcatAdapter.ViewHolder> {

    private final List<ExtrcatContent.CatItem> mValues;
    private final Context context;

    public ExtrcatAdapter(List<ExtrcatContent.CatItem> items, Context context) {
        mValues = items;
       this.context=context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_extrcat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).name);
        holder.extmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context,ExtdetailActivity.class);
                intent.putExtra("id",mValues.get(position).id);
                intent.putExtra("type",mValues.get(position).type);
                intent.putExtra("name",mValues.get(position).name);
                intent.putExtra("photo",mValues.get(position).image);
                intent.putExtra("price",mValues.get(position).price);
                intent.putExtra("description",mValues.get(position).description);
                context.startActivity(intent);
            }
        });

        Picasso.with(context).load(mValues.get(position).image).into(holder.mContentView);


    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final ImageView mContentView;
        public final TextView mTextView;
        public final LinearLayout extmenu;
        public ExtrcatContent.CatItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = view.findViewById(R.id.action_button);
            mTextView = view.findViewById(R.id.exttex);
            extmenu = view.findViewById(R.id.extmenu);
            mContentView = view.findViewById(R.id.card_image);


        }

        @Override
        public String toString() {
            return super.toString() + " '" + mIdView.getText() + "'"+ mTextView.getText() + "'";
        }
    }
}

