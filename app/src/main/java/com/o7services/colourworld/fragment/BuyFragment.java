package com.o7services.colourworld.fragment;


import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.o7services.colourworld.HomeActivity;
import com.o7services.colourworld.R;
import com.o7services.colourworld.connection.Connection;
import com.o7services.colourworld.customclass.CartContent;
import com.o7services.colourworld.shareddata.ShareData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class BuyFragment extends Fragment{

    RelativeLayout progress;
    ArrayList<String> arrayList = new ArrayList<>();
    String dates;
    Button button;
    ShareData shareManager;
    EditText item_address,item_number;

    TextView textView;
    public BuyFragment() {
        // Required empty public constructor
    }

    Button buttons;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_buy, container, false);

        progress = view.findViewById(R.id.progressBar);
      //  spinner = view.findViewById(R.id.spinner_time);
       // datee = view.findViewById(R.id.datee);
        item_address = view.findViewById(R.id.item_address);
        item_number = view.findViewById(R.id.item_number);
        button = view.findViewById(R.id.button);
        textView = view.findViewById(R.id.address);
        shareManager = new ShareData(getContext());

        buttons = view.findViewById(R.id.updated);
        buttons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item_address.setText(shareManager.getAddress());
            }
        });

        textView.setText(shareManager.getAddress());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!shareManager.getUserEmailId().equals("")) {
                    CheckoutCart checkoutCart = new CheckoutCart();
                    checkoutCart.execute(shareManager.getUserEmailId(), item_address.getText().toString(), item_number.getText().toString());
                }
                else
                {
                    Toast.makeText(getContext(), "Please Login First", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }


    public class CheckoutCart extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            progress.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                String user_id = strings[0];
                String user_address = strings[1];
                String user_number  = strings[2];
                String link = Connection.API+"cart.php";
                ArrayList<CartContent.ProductCart> list = CartContent.CARTLIST;
                JSONArray array=new JSONArray();
                for(int i=0;i<list.size();i++)
                {
                    JSONObject object=new JSONObject();
                    object.put("Prodid", list.get(i).productId);
                    object.put("qnty", list.get(i).quantity);
                    object.put("price",String.valueOf(list.get(i).productPrice));
                    array.put(object);
                }
                JSONObject object=new JSONObject();
                object.put("Total_Items", list.size());
                object.put("Total_Amount", CartContent.TOTALAMOUNT);
                object.put("User_Id",user_id);
                object.put("book_date",user_address);
                object.put("book_time",user_number);
                array.put(object);
                String msg=array.toString();
                Log.e("Message", msg);
                String data = URLEncoder.encode("json", "UTF-8")+"="+URLEncoder.encode(msg,"UTF-8");

                URL url = new URL(link);

                URLConnection connection = url.openConnection();
//                connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
//                connection.setRequestProperty("X-Requested-With", "XMLHttpRequest");
                connection.setDoOutput(true);

                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(data);
                writer.flush();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));


                return reader.readLine();
            }
            catch (Exception e)
            {
                return "Error! "+e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            progress.setVisibility(View.GONE);
            Log.e("Output",result);

            if (result.equals("success"))
            {
                CartContent.CARTLIST.clear();
                CartContent.TOTALAMOUNT=0;
                HomeActivity.notificationCountCart=0;
                AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                        .setIcon(R.mipmap.ic_launcher_round)
                        .setTitle("Message!")
                        .setMessage("Your Order has Successfully Submit\n\n we will immediately check the conditions, the availability" +
                                " , as well as the appointment of the products that you" +
                                " yourselves message, therefore DO NOT send / transfer money before there is confirmation from" +
                                " us via phone / SMS or email.")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Intent intent = new Intent(getContext(), HomeActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                ActivityOptions activityOptions = ActivityOptions.makeCustomAnimation(getContext(), android.R.anim.fade_in, android.R.anim.fade_out);
                                startActivity(intent, activityOptions.toBundle());
                                getActivity().finish();
                            }
                        })
                        .show();
            }
            else
            {
                AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                        .setIcon(R.mipmap.ic_launcher_round)
                        .setTitle("Alert!")
                        .setMessage("Your Order has not Successfully Submit \n Please Try Again")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
            }
        }
    }
}




