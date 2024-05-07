package com.o7services.colourworld.fragment;


import android.app.ActivityOptions;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForgetFragment extends Fragment {
    private EditText text_email;
    private RelativeLayout relativeLayout;
Button forget;
TextView login;

    public ForgetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_forget, container, false);

        text_email = v.findViewById(R.id.input_email);
        relativeLayout = v.findViewById(R.id.progressBar);
        forget=v.findViewById(R.id.forget);
        login=v.findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager frag=getActivity().getSupportFragmentManager();
                frag.beginTransaction().replace(R.id.content,new LoginFragment()).commit();
            }
        });
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                view = null;
                boolean flag = false;
                String email = text_email.getText().toString();

                if (TextUtils.isEmpty(email))
                {
                    text_email.setError("Enter your Email");
                    view = text_email;
                    flag = true;
                }
                else if (!email.matches(emailPattern))
                {
                    text_email.setError("Enter valid Email");
                    view = text_email;
                    flag = true;
                }

                if (flag)
                {
                    view.requestFocus();
                }
                else {
                    if (NetworkUtils.isNetworkAvailable(getContext())) {
                        ForgetData insertData = new ForgetData(relativeLayout);
                        insertData.execute(email);
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
                user_email = strings[0];
                String apis = Connection.API+"forget.php?email="+strings[0];

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



