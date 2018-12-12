package com.khn.mydbapp.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.khn.mydbapp.models.User;

public class SharedPrefManager {
    private static final String SHARED_PREF_NAME="my_shared_preff";
    private static SharedPrefManager mInstance;
    private Context mCtx;
    //-------------------------------------------
    private SharedPrefManager(Context mCtx){
        this.mCtx=mCtx;
    }
    //-------------------------------------------
    public static synchronized SharedPrefManager getmInstance(Context mCtx){
        if(mInstance == null)
            mInstance = new SharedPrefManager(mCtx);

        return mInstance;
    }
    //-------------------------------------------
    public void saveUser(User user){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("username",user.getUsername());
        editor.putString("email",user.getEmail());
        editor.putString("fullname",user.getFullname());
        editor.putString("createdAt",user.getCreatedAt());
        editor.apply();
    }
    //-------------------------------------------
    public boolean isNotLoggedIn(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getString("username","").isEmpty())
           return false;
        else
            return true;
    }
    //-------------------------------------------
    public User getUser(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getString("username",null),
                sharedPreferences.getString("email",null),
                sharedPreferences.getString("fullname",null),
                sharedPreferences.getString("createdAt",null)
        );
    }
    //-------------------------------------------
    public void clear(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
