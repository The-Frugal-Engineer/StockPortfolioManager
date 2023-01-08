package com.sarmale.stockportfoliomanager.auxelements;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;

public class SaveAppPreferences {
    Context context;

    public SaveAppPreferences(Context context){
        this.context=context;
    }

    public void saveAPICredentials (String host, String apiKey){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(AuxClass.SHARED_PREF_HOST,host);
        editor.putString(AuxClass.SHARED_PREF_API_KEY,apiKey);
        editor.apply();


    }
    public ArrayList<String> getAPICredentials (){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        ArrayList<String> credentials = new ArrayList<String>();

        credentials.add(0,preferences.getString(AuxClass.SHARED_PREF_HOST,null));
        credentials.add(1,preferences.getString(AuxClass.SHARED_PREF_API_KEY,null));

        return credentials;


    }

}
