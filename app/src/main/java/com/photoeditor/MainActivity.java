package com.photoeditor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.navigation.NavigationView;
import com.photoeditor.Adapter.ItemAdapter;
import com.photoeditor.Adapter.ItemModel;
import com.photoeditor.Utils.Constant;
import com.photoeditor.Utils.Loader;
import com.photoeditor.Utils.SaveData;
import com.photoeditor.Utils.Setting;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    RecyclerView rv;
    List<ItemModel> itemModelList;
    ItemAdapter itemAdapter;

    FrameLayout inject;

    Loader loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        String packageName = getApplication().getPackageName();


        final DrawerLayout mDrawerLayout = findViewById(R.id.drawer);

        inject = findViewById(R.id.inject);
        loader = new Loader(this,inject);

        ImageView menu = findViewById(R.id.menu);



        menu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mDrawerLayout.openDrawer(GravityCompat.START);

            }
        });

        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(item -> {

            JSONObject object ;
            try {

                  object = new JSONObject(new SaveData(MainActivity.this).getData("app"));


            if (item.getItemId() == R.id.rateapps) {

                gotoUrl("https://play.google.com/store/apps/details?id=" + packageName);

            }


            if (item.getItemId() == R.id.moreapps) {

                 gotoUrl(object.getString("play_console"));

            }
            if (item.getItemId() == R.id.shareapps) {

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,"https://play.google.com/store/apps/details?id=" + packageName);
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, "Share with"));

            }
            if (item.getItemId() == R.id.facebookpage) {

                gotoUrl(object.getString("facebook_page"));

            }
            if (item.getItemId() == R.id.facebookgroup) {

                gotoUrl(object.getString("facebook_group"));

            }
            if (item.getItemId() == R.id.privacy) {
                gotoUrl(object.getString("privacy"));
            }
            } catch (JSONException e) {

            }

            mDrawerLayout.close();

            return true;


        });


        itemModelList = new ArrayList<>();
        rv = findViewById(R.id.rv);
        itemAdapter = new ItemAdapter(itemModelList);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(itemAdapter);


        load();

        loader.addCallback(new Loader.onClick() {
            @Override
            public void refresh() {
                load();
            }
        });


    }




    void load(){


        loader.loading();


        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Constant.getUrl("1JHnJL3TkJY9zFmn0KksjdOjZ7XT7U5p4"), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                loader.remove();

                try {
                    new SaveData(MainActivity.this).save("app", response.toString());
                    new SaveData(MainActivity.this).save("json", response.get("app").toString());
                    loadBanner();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                setData();


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                loader.noInternet();

            }
        });

        queue.add(jsonObjectRequest);



    }

    @SuppressLint("NotifyDataSetChanged")
    private void setData() {

        try {


            JSONArray array = new JSONArray(new SaveData(this).getData("json"));

            for (int i=0; i < array.length(); i++){

                JSONObject object = array.getJSONObject(i);

                ItemModel model = new ItemModel(object.getString("title"),Constant.getUrl(object.getString("cover")));
                itemModelList.add(model);

            }

            itemAdapter.notifyDataSetChanged();


        } catch (JSONException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }


    private void gotoUrl(String s){

        try {
            if (!URLUtil.isValidUrl(s)) {
                Toast.makeText(this, " This is not a valid link", Toast.LENGTH_LONG).show();
            } else {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(s));
                startActivity(intent);
            }
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, " You don't have any browser to open web page", Toast.LENGTH_LONG).show();
        }


    }

    private void getAds() {

        String adData = new  SaveData(this).getData("app");

        if (!adData.isEmpty()){
            try {

                JSONObject object  = new JSONObject(adData);


                try {
                    ApplicationInfo applicationInfo = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
                    Bundle bundle = applicationInfo.metaData;
                    applicationInfo.metaData.putString("com.google.android.gms.ads.APPLICATION_ID", object.getString("app_id"));
                    String apiKey = bundle.getString("com.google.android.gms.ads.APPLICATION_ID");

                    Setting.admob_native_id = object.getString("banner_id");
                    Setting.admob_interstitial_id = object.getString("interstitial_id");




                } catch (PackageManager.NameNotFoundException | NullPointerException e) {

                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();

                }

            } catch (JSONException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }



    }

    private void loadBanner() {

        FrameLayout mAdView = findViewById(R.id.adView);

        AdView adView = findViewById(R.id.ad);

//        AdView adView = new AdView(this);
//
//        adView.setAdSize(AdSize.BANNER);
//
//        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");

        AdRequest adRequest = new AdRequest.Builder().build();

        adView.loadAd(adRequest);

         adView.setAdListener(new AdListener() {
             @Override
             public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                 super.onAdFailedToLoad(loadAdError);
                 Log.e("ERROR",loadAdError.getMessage());
                 Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
             }

             @Override
             public void onAdLoaded() {
                 super.onAdLoaded();
                 Toast.makeText(MainActivity.this, "Loaded", Toast.LENGTH_SHORT).show();
             }
         });

//        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
//
//        mAdView.addView(adView,params);
//



    }



}