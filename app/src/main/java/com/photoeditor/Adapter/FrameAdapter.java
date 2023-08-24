package com.photoeditor.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.photoeditor.R;

import java.util.ArrayList;
import java.util.List;

public class FrameAdapter extends RecyclerView.Adapter<FrameAdapter.Vh> {


    List list = new ArrayList();
    public FrameAdapter(List list) {

        this.list = list;


    }

    @NonNull
    @Override
    public FrameAdapter.Vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample,parent,false);



        return new Vh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FrameAdapter.Vh holder, int position) {

        holder.banner.setImageResource((Integer) list.get(position));

        holder.itemview.setOnClickListener(v -> {


            Toast.makeText(v.getContext(), ""+ position, Toast.LENGTH_SHORT).show();


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
}
