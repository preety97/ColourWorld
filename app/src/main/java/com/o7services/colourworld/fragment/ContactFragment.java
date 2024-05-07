package com.o7services.colourworld.fragment;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.o7services.colourworld.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {
     ImageView num;

    public ContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_contact, container, false);

        num = view. findViewById(R.id.cal);

    num.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
            // Setting Dialog Title
            alertDialog.setTitle("Do you want to call?");
            // Setting Dialog Message
            alertDialog.setMessage("+91 9888937673");
            // Setting Icon to Dialog
            //alertDialog.setIcon(R.drawable.warning);
            // Setting Negative "NO" Button
            alertDialog.setNegativeButton("No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            // Write your code here to execute after dialog
                            dialog.cancel();
                        }
                    });
            // Setting Positive "Yes" Button
            alertDialog.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            // Write your code here to execute after dialog
                            Intent callIntent = new Intent(Intent.ACTION_DIAL);
                            callIntent.setData(Uri.parse("91 9888937673"));
                            callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(callIntent);

                        }
                    });

            // Showing Alert Message
            alertDialog.show();
        }
    });



            return view;
        }

    }


