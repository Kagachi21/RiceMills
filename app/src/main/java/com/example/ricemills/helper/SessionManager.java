package com.example.ricemills.helper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.ricemills.Activity.LoginActivity;
import com.example.ricemills.MainActivity;

public class SessionManager {
    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;

    public static final String PREF_NAME = "LOGIN";
    public static final String LOGIN_STATUS = "LOGIN_STATUS";
    public static final String USERNAME = "USERNAME";
    public static final String NAMA = "NAMA";
    public static final String ID = "ID";

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void createSession(String username, String nama, String id) {
        editor.putBoolean(LOGIN_STATUS, true);
        editor.putString(USERNAME, username);
        editor.putString(NAMA, nama);
        editor.putString(ID, id);
        editor.apply();
    }

    public boolean isLogin(){
        return sharedPreferences.getBoolean(LOGIN_STATUS, false);
    }

    public void logout(){
        editor.clear();
        editor.commit();

        Intent login = new Intent(context, LoginActivity.class);
        context.startActivity(login);
        ((MainActivity)context).finish();
    }

    public String getEMAIL() {
        return sharedPreferences.getString(USERNAME, null);
    }

    public String getNamaPengguna() {
        return sharedPreferences.getString(NAMA, null);
    }

    public String getIdPengguna() {
        return sharedPreferences.getString(ID, null);
    }

}
