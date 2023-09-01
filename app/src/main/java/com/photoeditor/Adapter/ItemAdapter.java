package com.photoeditor.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.photoeditor.Frame;
import com.photoeditor.R;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.VH> {

    List<ItemModel> itemModelList;
    Context context;


    public ItemAdapter(List<ItemModel> itemModelList) {
        this.itemModelList = itemModelList;
    }

    @NonNull
    @Override
    public ItemAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.VH holder, int position) {

        Glide.with(context).load(itemModelList.get(position).getIcon()).into(holder.frame);
        holder.title.setText(itemModelList.get(position).getTitle());
        holder.item.setOnClickListener(v->{



            Intent i = new Intent(context, Frame.class);
            i.putExtra("title",itemModelList.get(position).getTitle());
            context.startActivity(i);


        });

    }

    @Override
    public int getItemCount() {
        return itemModelList.size();
    }

    public static class VH extends RecyclerView.ViewHolder{

        ImageView frame;
        TextView title;
        CardView item;

        public VH(@NonNull View itemView) {
            super(itemView);

            frame = itemView.findViewById(R.id.frame);
            title = itemView.findViewById(R.id.title);
            item = itemView.findViewById(R.id.item);



        }



    }






}
