package com.o7services.colourworld.adaptors;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.o7services.colourworld.ProductdetailActivity;
import com.o7services.colourworld.R;
import com.o7services.colourworld.customclass.KidContent;

import com.squareup.picasso.Picasso;

import java.util.List;


public class KidAdapter extends RecyclerView.Adapter<KidAdapter.ViewHolder> {

    private final List<KidContent.KidItem> mValues;
   Context context;

    public KidAdapter(List<KidContent.KidItem> items, Context context) {
        mValues = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_kid, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).name);
        Picasso.with(context).load(mValues.get(position).image).into(holder.mContentView);

        holder.menu_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ProductdetailActivity.class);
                intent.putExtra("id",mValues.get(position).id);
                intent.putExtra("type",mValues.get(position).type);
                intent.putExtra("name",mValues.get(position).name);
                intent.putExtra("photo",mValues.get(position).image);
                intent.putExtra("price",mValues.get(position).price);
                intent.putExtra("description",mValues.get(position).description);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final ImageView mContentView;
        public KidContent.KidItem mItem;
        public LinearLayout menu_linear;

        // public final View mView;
        // public final TextView mIdView;
        // public final TextView mContentView;
        // public DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.kidtext);
            mContentView = (ImageView) view.findViewById(R.id.kidimage);

            menu_linear = view.findViewById(R.id.kidmenu);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mIdView.getText() + "'";
        }
    }
}

