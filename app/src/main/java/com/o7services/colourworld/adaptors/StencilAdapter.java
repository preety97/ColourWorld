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
import com.o7services.colourworld.customclass.StencilContent;

import com.squareup.picasso.Picasso;

import java.util.List;


public class StencilAdapter extends RecyclerView.Adapter<StencilAdapter.ViewHolder> {

    private final List<StencilContent.StencilItem> mValues;
    Context context;
    public StencilAdapter(List<StencilContent.StencilItem> items, Context context) {
        mValues = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_stencil, parent, false);
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
        public StencilContent.StencilItem mItem;
        public LinearLayout menu_linear;

        // public final View mView;
        // public final TextView mIdView;
        // public final TextView mContentView;
        // public DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.stetext);
            mContentView = (ImageView) view.findViewById(R.id.steimage);

            menu_linear = view.findViewById(R.id.stemenu);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mIdView.getText() + "'";
        }
    }
}
