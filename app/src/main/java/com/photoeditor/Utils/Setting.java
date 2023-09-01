package com.photoeditor.Utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;

import org.json.JSONArray;
import org.json.JSONException;


public class Setting {

    public static String APP_ID = "";
    public static String admob_native_id = "";
    public static String admob_interstitial_id = "";

    public static boolean IS_BANNER_AD_ON = false;
    public static boolean IS_INTERSTITIAL_AD_ON = false;

    public static int AD_LIMIT_ON = 12;
    public static int AD_LIMIT_ON_MORE = 8;


    static public String url = "https://admin.iqamasocial.com/menu";

    // Toast messages

    static public String error = "Something went wrong.";
    static public String no_network = "No network connection.";

    static public JSONArray subjects(Context context) {
        String profile = new SaveData(context).getData("subjects");
        if (profile != null){
            try {
                return  new JSONArray(profile);
            } catch (JSONException e) {
                e.printStackTrace();
                return  null;
            }
        }else {
            return  null;
        }

    }



    static public boolean isNetworkConnected(Activity activity) {
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public static int getScreenWidth(Context context){
        int width =  context.getResources().getDisplayMetrics().widthPixels;
        return  width;
    }


}
