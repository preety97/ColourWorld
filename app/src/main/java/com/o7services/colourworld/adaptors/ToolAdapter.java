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
import com.o7services.colourworld.ToolActivity;
import com.o7services.colourworld.customclass.MenuContent;
import com.o7services.colourworld.customclass.ToolContent;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class ToolAdapter extends RecyclerView.Adapter<ToolAdapter.ViewHolder> {

    private final ArrayList<ToolContent.ToolItem> mValues;
    Context context;

    public ToolAdapter(ArrayList<ToolContent.ToolItem> items, Context context) {
        mValues = items;
        this.context=context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_tool, parent, false);
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
                intent.putExtra("tool",mValues.get(position).name);
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
        public ToolContent.ToolItem mItem;
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