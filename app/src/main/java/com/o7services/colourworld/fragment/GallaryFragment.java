package com.o7services.colourworld.fragment;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.o7services.colourworld.R;
import com.o7services.colourworld.connection.Connection;
import com.o7services.colourworld.interconnections.Response;
import com.o7services.colourworld.network.NetworkUtils;

import junit.framework.Assert;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class GallaryFragment extends Fragment implements Response{

    RelativeLayout prgLoading;
    public GallaryFragment() {
        // Required empty public constructor
    }
    //private ArrayList<String> _images;
    private ViewPager _pager;
    private LinearLayout _thumbnails;
    ArrayList<String> gallery = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gallary, container, false);

        ImageButton closeButton,refreshButton;

        _pager = view.findViewById(R.id.pager);
        _thumbnails = view.findViewById(R.id.thumbnails);
        refreshButton = view.findViewById(R.id.btn_refresh);
        prgLoading = view.findViewById(R.id.progress);

        FetchGallery gallery = new FetchGallery();
        gallery.response = GallaryFragment.this;
        gallery.execute();


        closeButton = view.findViewById(R.id.btn_close);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    getActivity().onBackPressed();

                }
                catch (Exception e){
                    Log.e("Error",e.getMessage());
                }
            }
        });

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FetchGallery gallery = new FetchGallery();
                gallery.response = GallaryFragment.this;
                gallery.execute();

            }
        });
        return view;
    }

    private void load_Images(ArrayList<String> arrayList)
    {
        GalleryPagerAdapter _adapter;
        if (!isNetworkAvailable())
        {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
            alertDialog.setIcon(R.mipmap.ic_launcher_round);
            alertDialog.setTitle("SETTINGS");
            alertDialog.setMessage("Enable Mobile Network/Wi-fi");
            alertDialog.setPositiveButton("Settings",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(
                                    Settings.ACTION_WIFI_SETTINGS);
                            startActivity(intent);
                        }
                    });
            alertDialog.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            Toast.makeText(getContext(), "Please On Internet Connection", Toast.LENGTH_SHORT).show();
                        }
                    });
            alertDialog.show();
        }
        else
        {
//            _images = new ArrayList<>();
//            _images.add("http://punjabnewsexpress.com/images/article/article60803.jpg");
//            _images.add("https://content2.jdmagicbox.com/comp/mohali/t6/0172px172.x172.140724163032.s2t6/catalogue/rayat-bahra-university-kharar-mohali-schools-4klgybg.jpg");
//            _images.add("http://www.bfcap.rayatbahra.com/facilities_images/rayat-bahra-class.jpg");
//            _images.add("http://www.royalpatiala.in/wp-content/uploads/2018/03/IMG_1047-780x405.jpg");
//            _images.add("http://punjabnewsexpress.com/images/article/article69941.jpg");
//            _images.add("http://www.jeduka.com/storage/school_gallery/2/rayat-bahra-group-of-institutes--mohali-4.jpg");

            Assert.assertNotNull(arrayList);

            _adapter = new GalleryPagerAdapter(getContext());
            _pager.setAdapter(_adapter);
            _pager.setOffscreenPageLimit(arrayList.size()); // how many images to load into memory
        }

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void processFinish(String result) {

        try {
            gallery.clear();
            JSONArray jsonArray = new JSONArray(result);
            JSONObject jsonObject;
            for (int i=0;i<jsonArray.length();i++)
            {
                jsonObject = jsonArray.getJSONObject(i);
                gallery.add(Connection.IMAGE_API+jsonObject.getString("image"));
            }
            load_Images(gallery);
        }
        catch (Exception e)
        {
            e.getMessage();
        }

    }

    class GalleryPagerAdapter extends PagerAdapter {

        Context _context;
        LayoutInflater _inflater;

        private GalleryPagerAdapter(Context context) {
            _context = context;
            _inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return gallery.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == (object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View itemView = _inflater.inflate(R.layout.pager_gallery_item, container, false);
            container.addView(itemView);

            // Get the border size to show around each image
            int borderSize = _thumbnails.getPaddingTop();

            // Get the size of the actual thumbnail image
            int thumbnailSize = ((FrameLayout.LayoutParams)
                    _pager.getLayoutParams()).bottomMargin - (borderSize*2);

            // Set the thumbnail layout parameters. Adjust as required
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(thumbnailSize, thumbnailSize);
            params.setMargins(0, 0, borderSize, 0);

            // You could also set like so to remove borders
            //ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
            //        ViewGroup.LayoutParams.WRAP_CONTENT,
            //        ViewGroup.LayoutParams.WRAP_CONTENT);

            final ImageView thumbView = new ImageView(_context);
            thumbView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            thumbView.setLayoutParams(params);
            thumbView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Set the pager position when thumbnail clicked
                    _pager.setCurrentItem(position);
                }
            });
            _thumbnails.addView(thumbView);

            final SubsamplingScaleImageView imageView = itemView.findViewById(R.id.image);

            // Asynchronously load the image and set the thumbnail and pager view
            Glide.with(_context)
                    .load(gallery.get(position))
                    .asBitmap()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                            imageView.setImage(ImageSource.bitmap(bitmap));
                            thumbView.setImageBitmap(bitmap);
                        }
                    });

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }

    public class FetchGallery extends AsyncTask<String,Void,String>
    {
        Response response = null;
        @Override
        protected void onPreExecute() {

            prgLoading.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {

                String apis = Connection.API+"gallery.php";
                apis = apis.replaceAll(" ","%20");
                URL url = new URL(apis);

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
            response.processFinish(s);

        }
    }
}
