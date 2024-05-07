package com.o7services.colourworld.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.o7services.colourworld.R;
import com.o7services.colourworld.adaptors.ExtrcatAdapter;
import com.o7services.colourworld.connection.Connection;
import com.o7services.colourworld.customclass.ExtrcatContent;
import com.o7services.colourworld.interconnections.Response;
import com.o7services.colourworld.network.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ExtrcatFragment extends Fragment implements Response {


    private int mColumnCount = 1;

    ProgressBar prgLoading;
    TextView txtAlert;
    TextView exttex;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout = null;

    public ExtrcatFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_extrcat_list, container, false);

        this.prgLoading = (ProgressBar) view.findViewById(R.id.prgLoading);
        this.txtAlert = (TextView) view.findViewById(R.id.txtAlert);
        this.exttex = (TextView) view.findViewById(R.id.exttex);

        final String strtext = getArguments().getString("category");
        // Set the adapter
        recyclerView = (RecyclerView) view.findViewById(R.id.list);

        exttex.setText(strtext);
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), mColumnCount));
        }


        if (NetworkUtils.isNetworkAvailable(getContext())) {
            FetchProduct insertData = new FetchProduct();
            insertData.response = ExtrcatFragment.this;
            insertData.execute(strtext);

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

        this.swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        this.swipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);
        this.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        recyclerView.setVisibility(View.GONE);
                        swipeRefreshLayout.setRefreshing(false);

                        FetchProduct insertData = new FetchProduct();
                        insertData.response = ExtrcatFragment.this;
                        insertData.execute(strtext);
                    }
                }, 3000);
            }
        });


        return view;
    }

    @Override
    public void processFinish(String result) {
        try {

            Log.e("Result", result);
            ExtrcatContent.ITEMS.clear();
            JSONArray jsonArray = new JSONArray(result);
            JSONObject jsonObject;

            for (int i = 0; i < jsonArray.length(); i++) {

                jsonObject = jsonArray.getJSONObject(i);

                ExtrcatContent.CatItem productItem = new ExtrcatContent.CatItem(jsonObject.getString("id"),
                        jsonObject.getString("type"),
                        jsonObject.getString("name"),
                        Connection.IMAGE_API + jsonObject.getString("image"),
                        jsonObject.getString("price"),
                        jsonObject.getString("description"));

                ExtrcatContent.ITEMS.add(productItem);

            }
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setAdapter(new ExtrcatAdapter(ExtrcatContent.ITEMS, getContext()));
        } catch (JSONException e) {
            e.printStackTrace();
            txtAlert.setVisibility(View.VISIBLE);
        }
    }

    public class FetchProduct extends AsyncTask<String, Void, String> {
        Response response = null;

        @Override
        protected void onPreExecute() {

            prgLoading.setVisibility(View.VISIBLE);
            txtAlert.setVisibility(View.GONE);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {

                String urls = Connection.API + "products.php?type=" + strings[0];
                urls = urls.replaceAll(" ", "%20");
                URL url = new URL(urls);

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

            prgLoading.setVisibility(View.GONE);
            try {
                response.processFinish(s);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}
