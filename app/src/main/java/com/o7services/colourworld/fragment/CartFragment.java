package com.o7services.colourworld.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.o7services.colourworld.HomeActivity;
import com.o7services.colourworld.R;
import com.o7services.colourworld.adaptors.CartAdapter;
import com.o7services.colourworld.customclass.CartContent;
import com.o7services.colourworld.shareddata.ShareData;

public class CartFragment extends Fragment {

    private int mColumnCount = 1;
    HomeActivity homeActivity;
    Spinner spin;
   String[] quantity={"1","2","3","4"};
    TextView amount;
    TextView checkout;
    ShareData shareManager;
    //TextView qty;
    Button button;
    //ImageButton adding;
    SharedPreferences sharedPreferences;

    public CartFragment() {
        homeActivity = new HomeActivity();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
        }
        LinearLayout layoutCartItems = (LinearLayout) view.findViewById(R.id.layout_items);
        LinearLayout layoutCartPayments = (LinearLayout) view.findViewById(R.id.layout_payment);
        LinearLayout layoutCartNoItems = (LinearLayout) view.findViewById(R.id.layout_cart_empty);

      //  spin=view.findViewById(R.id.spin);
      //  ArrayAdapter arrayAdapter=new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,quantity);
      //   spin.setAdapter(arrayAdapter);

        shareManager = new ShareData(getContext());
        button = view.findViewById(R.id.bAddNew);
        amount = view.findViewById(R.id.text_action_bottom1);
        checkout = view.findViewById(R.id.text_action_bottom2);
       // adding = view.findViewById(R.id.adding);
        //qty = view.findViewById(R.id.qty);
//qty.setText((CharSequence) spin);


        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
   //     fragmentTransaction.setCustomAnimations(R.anim.fade_in,R.anim.fade_out);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction.replace(R.id.content,new CategoryFragment()).commit();
            }
        });

        if(HomeActivity.notificationCountCart >0){
            layoutCartNoItems.setVisibility(View.GONE);
            layoutCartItems.setVisibility(View.VISIBLE);
            layoutCartPayments.setVisibility(View.VISIBLE);
        }else {
            layoutCartNoItems.setVisibility(View.VISIBLE);
            layoutCartItems.setVisibility(View.GONE);
            layoutCartPayments.setVisibility(View.GONE);
        }

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (HomeActivity.notificationCountCart>0) {
                    if (!shareManager.getUserEmailId().equals("")) {
                        fragmentTransaction.replace(R.id.content, new BuyFragment()).commit();
                    }
                    else
                    {
                        Toast.makeText(getContext(), "Please Login First", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getContext(), "Cart is Empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

      /*  adding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (qty == null) {
                    qty.setText("");
                } else {
                    int opt1 = Integer.valueOf(qty.getText().toString());
                    opt1=1;
                    qty.setText(opt1++);

                }
            }
        });*/

        amount.setText("Rs. "+String.valueOf(CartContent.TOTALAMOUNT));

        RecyclerView recyclerView = view.findViewById(R.id.list);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setAdapter(new CartAdapter(CartContent.CARTLIST, getContext(),amount));

        return view;
    }

}
