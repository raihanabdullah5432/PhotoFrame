package com.photoeditor.Utils;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.photoeditor.R;
import com.skydoves.elasticviews.ElasticLayout;

public class Loader {

    public FrameLayout inject;
    private Context context;
    private View view;
    public ElasticLayout refresh;
    private onClick callback;

    public Loader(Context context, FrameLayout inject) {

        this.context = context;
        this.inject = inject;


        LayoutInflater inflater =(LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
        view =   inflater.inflate(R.layout.refresh, null);
        inject.addView(view);
        refresh = view.findViewById(R.id.refresh);
        refresh.setOnClickListener(v->{

            loading();
            if (callback != null) callback.refresh();

        });


    }


    public void loading(){

        if (view != null){
            inject.setVisibility(View.VISIBLE);
            LinearLayout no_internet  =  view.findViewById(R.id.no_internet);
            ProgressBar progressBar = view.findViewById(R.id.progressBar);
            no_internet.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }


    }

    public void noInternet(){

        if (view != null){
            inject.setVisibility(View.VISIBLE);
            LinearLayout no_internet  =  view.findViewById(R.id.no_internet);
            ProgressBar progressBar = view.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.GONE);
            no_internet.setVisibility(View.VISIBLE);

        }


    }



    public void remove(){

        if (view != null){
           inject.setVisibility(View.GONE);
        }


    }
    public void addCallback(onClick callback){
        this.callback = callback;
    }

    public interface onClick{
        void refresh();
    }


}
