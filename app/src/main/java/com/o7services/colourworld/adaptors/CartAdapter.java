package com.o7services.colourworld.adaptors;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.o7services.colourworld.HomeActivity;
import com.o7services.colourworld.R;
import com.o7services.colourworld.customclass.CartContent;
import com.o7services.colourworld.fragment.CartFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    CartFragment cartFragment = new CartFragment();

    ArrayList<CartContent.ProductCart> mValues = new ArrayList<>();
    Context context;
    TextView total_amount;
    public CartAdapter(ArrayList<CartContent.ProductCart> items, Context listener, TextView amount) {
        mValues = items;
        context = listener;
        total_amount = amount;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.item_name.setText(mValues.get(position).productName);
        holder.item_price.setText("Rs. "+mValues.get(position).productPrice);
        holder.mContentView.setText(mValues.get(position).productDescription);
        Picasso.with(context).load(mValues.get(position).productImage).resize(150,150).centerCrop().into(holder.item_image);
        holder.remove_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartContent.TOTALAMOUNT -= Integer.parseInt(mValues.get(position).productPrice);
                HomeActivity.notificationCountCart--;
                CartContent.CARTLIST.remove(position);
                total_amount.setText("Rs. "+String.valueOf(CartContent.TOTALAMOUNT));
                notifyDataSetChanged();

            }
        });
        holder.qty.setText("Quantity : "+mValues.get(position).quantity);

       //ArrayAdapter arrayAdapter=new ArrayAdapter(context,android.R.layout.simple_list_item_1);
      // holder. qty.setAdapter(arrayAdapter);
       // holder.qty.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        //    @Override
         //   public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

           // }
        //});

    }

    @Override
    public int getItemCount() {
//        if (HomeActivity.notificationCountCart<1)
//        {
//            cartFragment.recallMethod();
//        }
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView item_name;
        public final TextView mContentView;
        public CartContent.ProductCart mItem;
        public LinearLayout remove_item;
        public final TextView item_price;
        public final ImageButton item_qnty;
        public final ImageView item_image;
        public final TextView qty;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            item_name = (TextView) view.findViewById(R.id.item_name);
            mContentView = (TextView) view.findViewById(R.id.content);
            remove_item = view.findViewById(R.id.layout_action1);
            item_price = view.findViewById(R.id.item_price);
            item_qnty = view.findViewById(R.id.add);
            item_image = view.findViewById(R.id.image_cartlist);
            qty = view.findViewById(R.id.spin);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
