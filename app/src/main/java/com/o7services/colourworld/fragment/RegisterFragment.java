package com.o7services.colourworld.fragment;


import android.os.AsyncTask;
import android.os.Bundle;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.o7services.colourworld.R;
import com.o7services.colourworld.connection.Connection;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

    EditText name,city,address,email,contact,pass;
    RelativeLayout relativeLayout;
    TextView log;
    RadioGroup radioGroup;
    RadioButton radioButton;
    Button reg;


    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_register, container, false);

        relativeLayout = view.findViewById(R.id.relative);

        name = view.findViewById(R.id.name);
        city = view.findViewById(R.id.city);
        address = view.findViewById(R.id.address);
        contact = view.findViewById(R.id.contact);
        email = view.findViewById(R.id.email);
        reg = view.findViewById(R.id.reg);
      //  pass= view.findViewById(R.id.pass);
        log= view.findViewById(R.id.login);
        radioGroup=view.findViewById(R.id.radiogroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int radio=radioGroup.getCheckedRadioButtonId();
                radioButton=view.findViewById(radio);
            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                view = null;

                boolean flag= false;
                view=null;
                String tname = name.getText().toString();
                String tcity = city.getText().toString();
                String taddress = address.getText().toString();
                String data=radioButton.getText().toString();
                String tcontact = contact.getText().toString();
                String temail = email.getText().toString();
              //  String tpass = pass.getText().toString();

                if(TextUtils.isEmpty(tname))
                {
                    name.setError("Fill the name");
                    view=name;
                    flag=true;
                }
                if(TextUtils.isEmpty(tcity))
                {
                    city.setError("Fill the city");
                    view=email;
                    flag=true;
                }
                if(TextUtils.isEmpty(taddress))
                {
                    address.setError("Fill the address");
                    view=address;
                    flag=true;
                }
                if(TextUtils.isEmpty(data))
                {
                    radioButton.setError("Fill the gender");
                    view=radioButton;
                    flag=true;
                }
                if(!temail.matches(emailPattern))
                {
                    email.setError("Fill the email");
                    view=email;
                    flag=true;
                }


                if(TextUtils.isEmpty(tcontact))
                {
                    contact.setError("Fill the number");
                    view=contact;
                    flag=true;
                }
                else if (tcontact.length()<10)
                {
                    contact.setError("Incorrect number");
                    view = contact;
                    flag = true;
                }
             /*   if(TextUtils.isEmpty(tpass))
                {
                    pass.setError("Fill the password");
                    view=pass;
                    flag=true;
                }
                else if (tpass.length() < 6)
                {
                    pass.setError("Password should at least 6 char length");
                    view = pass;
                    flag = true;
                }*/
                else
                {
                    Toast.makeText(getContext(),"succes",Toast.LENGTH_SHORT).show();
                    new InsertData().execute(tname,tcity,taddress,data,tcontact,temail);
                }
            }
        });
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager frag = getActivity().getSupportFragmentManager();
                frag.beginTransaction().replace(R.id.content, new LoginFragment()).commit();
            }
        });
        return view;
    }

    class InsertData extends AsyncTask<String, Void , String> {

        @Override
        protected void onPreExecute() {
            relativeLayout.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                String name = strings[0];
                String city = strings[1];
                String address = strings[2];
                String gender = strings[3];
                String contact = strings[4];
                String email = strings[5];
                //String pass = strings[6];

                String apis = Connection.API+"register.php?name="
                        +name + "&city="+city+"&address="+address+"&gender="+gender+"&number="+contact +"&email="+email;
                Log.e("URL", apis);

                URL url = new URL(apis);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);

                Log.e("URL", String.valueOf(connection));

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

           String res = s.replaceAll(" ", "");
            Log.e("Result",res);
            if (res.equals("Yes"))
            {
                Toast.makeText(getContext(), "yes", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getContext(), "no", Toast.LENGTH_SHORT).show();
            }
//            try{
//            JSONArray jsonArray = new JSONArray(res);
//            JSONObject jsonObject;
//
//            for (int i = 0; i < jsonArray.length(); i++) {
//
//                jsonObject = jsonArray.getJSONObject(i);
//                String name=jsonObject.getString("name");
//                String city=jsonObject.getString("city");
//                String address=jsonObject.getString("address");
//                String gender=jsonObject.getString("gender");
//                String number=jsonObject.getString("number");
//                String email=jsonObject.getString("email");
//                String pass=jsonObject.getString("password");
//
//            }
//            }
//            catch (Exception e)
//            {
//            e.printStackTrace();
//            }
        }

    }
}