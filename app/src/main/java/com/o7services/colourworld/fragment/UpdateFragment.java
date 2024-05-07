package com.o7services.colourworld.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.o7services.colourworld.R;
import com.o7services.colourworld.connection.Connection;
import com.o7services.colourworld.network.NetworkUtils;
import com.o7services.colourworld.shareddata.ShareData;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateFragment extends Fragment {
    private EditText old,newpass;
    private RelativeLayout relativeLayout;
    Button update;
    ShareData shareData;
    TextView login;

    public UpdateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_update, container, false);



        old = v.findViewById(R.id.old_pass);
        newpass = v.findViewById(R.id.new_pass);
        relativeLayout = v.findViewById(R.id.progressBar);
        update=v.findViewById(R.id.update);
        login=v.findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager frag=getActivity().getSupportFragmentManager();
                frag.beginTransaction().replace(R.id.content,new LoginFragment()).commit();
            }
        });

        shareData=new ShareData(getContext());
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view = null;
                boolean flag = false;
                String oldpas = old.getText().toString();

                String newpas = newpass.getText().toString();

                if (TextUtils.isEmpty(oldpas))
                {
                    old.setError("Enter your old password");
                    view = old;
                    flag = true;
                }

                if (TextUtils.isEmpty(newpas))
                {
                    newpass.setError("Enter your new password");
                    view = newpass;
                    flag = true;
                }
                else if (newpas.length()<8)
                {

                    Toast.makeText(getContext(),String.valueOf(newpas.length()) , Toast.LENGTH_SHORT).show();
                    newpass.setError("password should be in 8 character");
                    view = newpass;
                    flag = true;
                }

                if (flag)
                {
                    view.requestFocus();
                }
                else {
                    if (NetworkUtils.isNetworkAvailable(getContext())) {
                        UpdateFragment.ForgetData insertData = new UpdateFragment.ForgetData(relativeLayout);
                        insertData.execute(oldpas,newpas);
                    } else {
                        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                                .setIcon(R.mipmap.ic_launcher_round)
                                .setTitle("Alert!")
                                .setMessage("Turn on Internet Connection")
                                .setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(
                                                Settings.ACTION_WIFI_SETTINGS);
                                        startActivity(intent);
                                    }
                                })
                                .show();
                    }
                }
            }
        });

        return v;

    }

    class ForgetData extends AsyncTask<String,Integer,String>
    {
        RelativeLayout bar;
        String user_email;

        ForgetData(RelativeLayout rel)
        {
            bar = rel;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            bar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                user_email = shareData.getUserEmailId();
              String  password = strings[0];
              String  newpassword = strings[1];
                String apis = Connection.API+"uppass.php?email="+user_email+ "&password="+password+"&newpassword="+newpassword;

                Log.e("API",apis);
                URL url = new URL(apis);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                return reader.readLine();
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return "Error! "+e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {

            Log.e("Response",s);
            bar.setVisibility(View.GONE);
            try
            {
                if (s.equals("success"))
                {
                    AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                            .setIcon(R.mipmap.ic_launcher_round)
                            .setTitle("Message!")
                            .setMessage("Password Recover Successfully")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    FragmentManager frag=getActivity().getSupportFragmentManager();
                                    frag.beginTransaction().replace(R.id.content,new LoginFragment()).commit();
                                }
                            })
                            .show();

                }
                else
                {
                    Toast.makeText(getContext(), "Wrong Email\n Please Try Again", Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception e){
                Log.e("Error",e.getMessage());
                Toast.makeText(getContext(), "Please Try Again", Toast.LENGTH_SHORT).show();
            }
        }
    }

    }

