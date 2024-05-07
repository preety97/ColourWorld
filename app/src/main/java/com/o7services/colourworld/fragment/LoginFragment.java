package com.o7services.colourworld.fragment;


import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
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

import com.o7services.colourworld.HomeActivity;
import com.o7services.colourworld.R;
import com.o7services.colourworld.connection.Connection;
import com.o7services.colourworld.network.NetworkUtils;
import com.o7services.colourworld.shareddata.ShareData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.o7services.colourworld.R.*;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    private EditText text_email, text_password;
    private RelativeLayout relativeLayout;
    ShareData shareData;
    TextView forget, reg;
    Button login;


    public LoginFragment() {
        // Required empty public constructor
    }


    @SuppressLint("WrongViewCast")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(layout.fragment_login, container, false);
       // ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        ((HomeActivity) getActivity())
                .setActionBarTitle("LOGIN HERE");

        shareData = new ShareData(getContext());
        if (!shareData.getUserEmailId().equals(""))
        {
            Intent i = new Intent(getContext(),HomeActivity.class);
            startActivity(i);
            getActivity().finish();

        }

        text_email = v.findViewById(R.id.input_email);
        text_password = v.findViewById(R.id.input_password);
        relativeLayout = v.findViewById(R.id.progressBar);
        forget = v.findViewById(R.id.forget);
        reg = v.findViewById(id.register);
        login = v.findViewById(id.login);
       // update = v.findViewById(id.update);
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager frag = getActivity().getSupportFragmentManager();
                frag.beginTransaction().replace(R.id.content, new ForgetFragment()).commit();
            }

        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                view = null;
                boolean flag = false;
                String email = text_email.getText().toString();
                String password = text_password.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    text_email.setError("Enter your Email");
                    view = text_email;
                    flag = true;
                } else if (!email.matches(emailPattern)) {
                    text_email.setError("Enter valid Email");
                    view = text_email;
                    flag = true;
                } else if (TextUtils.isEmpty(password)) {
                    text_password.setError("Enter your Password");
                    view = text_password;
                    flag = true;
                } //else if (password.length() < 6) {
                  //  text_password.setError("Password should at least 6 char length");
                    //view = text_password;
                   // flag = true;
              //  }

                if (flag) {
                    view.requestFocus();
                } else {
                    if (NetworkUtils.isNetworkAvailable(getContext())) {
                        InsertData insertData = new InsertData(relativeLayout);
                        insertData.execute(email, password);
                    } else {
                        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                                .setIcon(mipmap.ic_launcher_round)
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

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager frag = getActivity().getSupportFragmentManager();
                frag.beginTransaction().replace(R.id.content, new RegisterFragment()).commit();
            }
        });

        return v;

    }


    class InsertData extends AsyncTask<String, Integer, String> {
        RelativeLayout bar;
        String email;
        String address;

        InsertData(RelativeLayout rel) {
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

                email = strings[0];

                String apis = Connection.API + "login.php?email=" + strings[0] + "&password=" + strings[1];
                        //+ "&address=" + strings[2];

                URL url = new URL(apis);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                return reader.readLine();
            } catch (Exception e) {
                e.printStackTrace();
                return "Error! " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {

            relativeLayout.setVisibility(View.GONE);
            Log.e("response", s);
           // JSONObject jsonObject = new JSONObject(s);

            try {

                JSONObject jsonObject = new JSONObject(s);

                if (jsonObject.getString("response").equals("success")) {
                    ShareData shareData = new ShareData(getContext());
                    shareData.setShared_name(email, "Color World", jsonObject.getString("address"));
                    Intent i = new Intent(getContext(), HomeActivity.class);
                    startActivity(i);
                    getActivity().finish();
                    Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                }

                else
                {

                }
                } catch (JSONException e1) {
                e1.printStackTrace();
                }

        }
    }

    }



