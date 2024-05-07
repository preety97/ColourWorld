package com.o7services.colourworld.fragment;

import android.app.AlertDialog;
import android.content.Context;
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
import com.o7services.colourworld.adaptors.ToolAdapter;
import com.o7services.colourworld.connection.Connection;
import com.o7services.colourworld.customclass.MenuContent;
import com.o7services.colourworld.customclass.ToolContent;
import com.o7services.colourworld.interconnections.Response;
import com.o7services.colourworld.network.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class ToolFragment extends Fragment implements Response {

    // TODO: Customize parameters
    private int mColumnCount = 1;

    ProgressBar prgLoading;
    TextView txtAlert;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout = null;

    public ToolFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tool_list, container, false);

        // Set the adapter
        this.prgLoading = (ProgressBar) view.findViewById(R.id.prgLoading);
        this.txtAlert = (TextView) view.findViewById(R.id.txtAlert);
        //final String strtext = getArguments().getString("tool");
        // Set the adapter
        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), mColumnCount));
        }

        if (NetworkUtils.isNetworkAvailable(getContext()))
        {
            FetchCategory insertData = new FetchCategory();
            insertData.response = ToolFragment.this;
            insertData.execute();

        }
        else
        {
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

                        FetchCategory insertData = new FetchCategory();
                        insertData.response = ToolFragment.this;
                        insertData.execute();
                    }
                },3000);
            }
        });


        return view;
    }

    @Override
    public void processFinish(String result) {
        try {
            ToolContent.ITEMS.clear();
            JSONArray jsonArray = new JSONArray(result);
            JSONObject jsonObject;

            for (int i = 0; i < jsonArray.length(); i++) {

                jsonObject = jsonArray.getJSONObject(i);

                ToolContent.ToolItem menuItem = new ToolContent.ToolItem(jsonObject.getString("id"),
                        jsonObject.getString("tool"),
                        Connection.IMAGE_API+jsonObject.getString("image"));

                ToolContent.ITEMS.add(menuItem);

            }
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setAdapter(new ToolAdapter((ArrayList<ToolContent.ToolItem>) ToolContent.ITEMS,getContext()));
        } catch (JSONException e) {
            e.getMessage();
            txtAlert.setVisibility(View.VISIBLE);
        }
    }

    public class FetchCategory extends AsyncTask<String,Void,String>
    {
        Response response = null;
        @Override
        protected void onPreExecute() {

            prgLoading.setVisibility(View.VISIBLE);
            txtAlert.setVisibility(View.GONE);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {

                URL url = new URL(Connection.API+"tool.php");

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);

                Log.e("URL",String.valueOf(connection));

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

            prgLoading.setVisibility(View.GONE);
            try {
                response.processFinish(s);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
}
