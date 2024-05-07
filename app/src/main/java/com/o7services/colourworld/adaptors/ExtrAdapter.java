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

import com.o7services.colourworld.ExteriorActivity;
import com.o7services.colourworld.ProductdetailActivity;
import com.o7services.colourworld.R;
import com.o7services.colourworld.customclass.MenuContent;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class ExtrAdapter extends RecyclerView.Adapter<ExtrAdapter.ViewHolder> {
    private final ArrayList<MenuContent.MenuItem> mValues;
    Context context;
    public ExtrAdapter(List<MenuContent.MenuItem> items, Context context) {
        mValues = (ArrayList<MenuContent.MenuItem>) items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_extr, parent, false);
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
                Intent intent = new Intent(context, ExteriorActivity.class);
                intent.putExtra("category",mValues.get(position).name);
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
        public MenuContent.MenuItem mItem;
        public LinearLayout menu_linear;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.categoryName);
            mContentView = (ImageView) view.findViewById(R.id.categoryImage);
            menu_linear = view.findViewById(R.id.menu_linear);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mIdView.getText() + "'";
        }
    }
}
