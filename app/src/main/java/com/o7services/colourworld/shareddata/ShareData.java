package com.o7services.colourworld.shareddata;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by user on 3/29/2018.
 */

public class ShareData
{
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String email_key = "email";
    String paddress = "address";

    private static final String USER_NAME = "UserName";
    private static final String shared_name = "ColourWorld";

    public ShareData(Context context)
    {
        sharedPreferences =context.getSharedPreferences(shared_name,0);
        editor = sharedPreferences.edit();
        editor.apply();
    }


    public void setShared_name(String email,String name,String address) {
        editor.putString(email_key, email);
        editor.putString(USER_NAME,name);
        editor.putString(paddress, address);
        editor.commit();
    }

    public String getUserEmailId(){
        return sharedPreferences.getString(email_key,"");
    }

    public String getUserName(){
        return sharedPreferences.getString(USER_NAME,"");
    }
    public String getAddress(){
        return sharedPreferences.getString(paddress,"");
    }

}
