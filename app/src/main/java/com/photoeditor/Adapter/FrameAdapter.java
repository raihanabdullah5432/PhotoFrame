package com.photoeditor.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.photoeditor.MakeFrameActivity;
import com.photoeditor.R;

import java.util.ArrayList;
import java.util.List;

public class FrameAdapter extends RecyclerView.Adapter<FrameAdapter.Vh> {

   Context context;
    List<FrameModel> list  ;
    public FrameAdapter( List<FrameModel> list) {

        this.list = list;


    }
    private OnClick cb;
    @NonNull
    @Override
    public FrameAdapter.Vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context = parent.getContext();

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample,parent,false);



        return new Vh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FrameAdapter.Vh holder, int position) {

        Glide.with(context).load(list.get(position).getFrame()).into(holder.banner);

        holder.itemview.setOnClickListener(v -> {

            if (cb != null) cb.click(list.get(position).getFrame(),list.get(position).getPosition(),list.get(position).getText1(),list.get(position).getText2());


        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Vh extends RecyclerView.ViewHolder {

        ImageView banner;
        LinearLayout itemview;
        public Vh(@NonNull View itemView) {
            super(itemView);

            banner = itemView.findViewById(R.id.frameBanner);
            itemview = itemView.findViewById(R.id.item);


        }
    }





    public void setCb(OnClick onClick){
        this.cb = onClick;
    }


    public interface OnClick{
        void click(String cover,String position, String text1,String text2);
    }


}
