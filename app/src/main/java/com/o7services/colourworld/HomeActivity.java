package com.o7services.colourworld;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.o7services.colourworld.fragment.AboutFragment;
import com.o7services.colourworld.fragment.CartFragment;
import com.o7services.colourworld.fragment.CategoryFragment;
import com.o7services.colourworld.fragment.BuyFragment;
import com.o7services.colourworld.fragment.CompanyFragment;
import com.o7services.colourworld.fragment.ContactFragment;
import com.o7services.colourworld.fragment.DesignFragment;
import com.o7services.colourworld.fragment.GallaryFragment;
import com.o7services.colourworld.fragment.HomeFragment;
import com.o7services.colourworld.fragment.LoginFragment;
import com.o7services.colourworld.fragment.ProfileFragment;
import com.o7services.colourworld.fragment.RegisterFragment;
import com.o7services.colourworld.fragment.UpdateFragment;
import com.o7services.colourworld.notificationclass.NotificationCountSetClass;
import com.o7services.colourworld.shareddata.ShareData;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static int notificationCountCart = 0;
    DrawerLayout drawer;
    Class fragmentClass;
    ShareData shareData;
    Fragment fragment;
    FragmentManager fragmentManager;
    boolean doubleBackToExitPressedOnce = false;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
       Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       setSupportActionBar(toolbar);
        shareData = new ShareData(this);
        fragmentManager = getSupportFragmentManager();
       // getActionBar().show();
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        Intent intent = getIntent();
        if (intent.hasExtra("cart"))
        {
            FragmentManager frag = getSupportFragmentManager();
            frag.beginTransaction().replace(R.id.content, new CartFragment()).commit();

        }
        else {
            FragmentManager frag = getSupportFragmentManager();
            frag.beginTransaction().replace(R.id.content, new HomeFragment()).commit();
        }
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Menu menu = navigationView.getMenu();
        MenuItem login = menu.findItem(R.id.login);
        MenuItem logout = menu.findItem(R.id.logout);
        MenuItem cart = menu.findItem(R.id.cart);
        MenuItem category = menu.findItem(R.id.category);
        MenuItem checkout = menu.findItem(R.id.checkout);
        MenuItem update = menu.findItem(R.id.update);
        MenuItem custom = menu.findItem(R.id.custom);


        View header = navigationView.getHeaderView(0);
        TextView name = header.findViewById(R.id.name);
        TextView email = header.findViewById(R.id.textView);

        Log.e("Email",shareData.getUserEmailId());
        if (shareData.getUserEmailId().equals(""))
        {

            login.setVisible(true);
            logout.setVisible(false);
            update.setVisible(false);
            custom.setVisible(false);

        }
        else
        {
            name.setText(shareData.getUserName());
            email.setText(shareData.getUserEmailId());
            login.setVisible(false);
            logout.setVisible(true);
            update.setVisible(true);
            custom.setVisible(true);
        }
        shareData = new ShareData(this);
        if (shareData.getUserEmailId().equals("")) {
            cart.setVisible(false);
            checkout.setVisible(false);
        }
        else
        {
            category.setVisible(false);
            cart.setVisible(false);
            checkout.setVisible(true);
        }

    }
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void onBackPressed() {
        String frag = getSupportFragmentManager().getFragments().toString();

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else if (!(frag.contains("HomeFragment")))
        {

           // Toast.makeText(this, frag, Toast.LENGTH_SHORT).show();
            try {

                fragmentClass = HomeFragment.class;
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                    fragmentManager.beginTransaction().replace(R.id.content, fragment).commit();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            catch (Exception e){
                Log.e("Error",e.getMessage());
            }
        }
        else {
            if(doubleBackToExitPressedOnce) {
                finish();
                System.exit(0);
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }
    public void logout()
    {
        shareData.setShared_name("","","");
        Intent intent =new Intent(this,HomeActivity.class);
        startActivity(intent);
    }
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem item = menu.findItem(R.id.action_cart);
        NotificationCountSetClass.setAddToCart(HomeActivity.this, item,notificationCountCart);

        invalidateOptionsMenu();
        return super.onPrepareOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_cart) {

            fragmentManager.beginTransaction().replace(R.id.content,new CartFragment()).commit();
        }

        return super.onOptionsItemSelected(item);

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

       /* if (id == R.id.color) {
            FragmentManager frag=getSupportFragmentManager();
            frag.beginTransaction().replace(R.id.content,new ColorFragment()).commit();
        }*/
         if (id == R.id.home) {

            FragmentManager frag=getSupportFragmentManager();
            frag.beginTransaction().replace(R.id.content,new HomeFragment()).commit();

        }
        else if (id == R.id.feedback) {

            Intent i=new Intent(Intent.ACTION_SEND);
            i.setData(Uri.parse("email"));
            String[] s={"to.er.preety@gmail.com"};
            i.putExtra(Intent.EXTRA_EMAIL,s);
            i.putExtra(Intent.EXTRA_SUBJECT,"Enter your Views");
            i.setType("message/rfc822");
            Intent chooser=Intent.createChooser(i,"Launch Email");
            startActivity(chooser);
        }
        else if (id == R.id.gallery) {
             FragmentManager frag=getSupportFragmentManager();
             frag.beginTransaction().replace(R.id.content,new GallaryFragment()).commit();

        } else if (id == R.id.category) {
            FragmentManager frag=getSupportFragmentManager();
            frag.beginTransaction().replace(R.id.content,new CategoryFragment()).commit();

        } else if (id == R.id.cart) {

            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content,new CartFragment()).commit();

        } else if (id == R.id.register) {
            FragmentManager frag=getSupportFragmentManager();
            frag.beginTransaction().replace(R.id.content,new RegisterFragment()).commit();


        } else if (id == R.id.login) {
            FragmentManager frag=getSupportFragmentManager();
            frag.beginTransaction().replace(R.id.content,new LoginFragment()).commit();

        }
         else if (id == R.id.custom) {
             FragmentManager frag=getSupportFragmentManager();
             frag.beginTransaction().replace(R.id.content,new DesignFragment()).commit();

         }
        else if (id == R.id.about) {
            FragmentManager frag=getSupportFragmentManager();
            frag.beginTransaction().replace(R.id.content,new AboutFragment()).commit();

        }
        else if (id == R.id.checkout) {
            FragmentManager frag=getSupportFragmentManager();
            frag.beginTransaction().replace(R.id.content,new BuyFragment()).commit();

        }
         else if (id == R.id.stuprofile) {
             FragmentManager frag=getSupportFragmentManager();
             frag.beginTransaction().replace(R.id.content,new ProfileFragment()).commit();

         }
         else if (id == R.id.update) {
             FragmentManager frag=getSupportFragmentManager();
             frag.beginTransaction().replace(R.id.content,new UpdateFragment()).commit();

         }

        // else if (id == R.id.comprofile) {
          //   FragmentManager frag=getSupportFragmentManager();
           //  frag.beginTransaction().replace(R.id.content,new CompanyFragment()).commit();

        // }
     else if (id == R.id.logout) {
      logout();

    }
        else if (id == R.id.share) {
            // Intent intent = new Intent(Intent.ACTION_VIEW);
           //  intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.android.example"));
            // startActivity(intent);
             Intent i = new Intent(Intent.ACTION_SEND);
             i.setType("text/plain");
             i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
             String appLink = "https://play.google.com/store/apps/details?id=" + getPackageName();
             i.putExtra(Intent.EXTRA_TEXT, appLink);
             startActivity(Intent.createChooser(i, getString(R.string.choose_one)));

        }
         else if (id == R.id.send) {
             Intent i = new Intent(Intent.ACTION_SEND);
             i.setType("text/plain");
             i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
             String appLink = "https://play.google.com/store/apps/details?id=" + getPackageName();
             i.putExtra(Intent.EXTRA_TEXT, appLink);
             startActivity(Intent.createChooser(i, getString(R.string.choose_one)));

         }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
    private Uri getPlayStoreLink() {
        return Uri.parse("market://details?id=" + getPackageName());
    }
}
