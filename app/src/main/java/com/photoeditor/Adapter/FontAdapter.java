package com.photoeditor.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.photoeditor.R;

import java.util.ArrayList;

public class FontAdapter extends RecyclerView.Adapter<FontAdapter.VH> {

    private ArrayList<String> arrayList = new ArrayList<>();
    private Context context;
    private OnSelect onSelect;

    @SuppressLint("SuspiciousIndentation")
    public FontAdapter(OnSelect onSelect) {
            this.onSelect = onSelect;

            this.arrayList.add("font/hellofont6.otf");
            this.arrayList.add("font/font1.ttf");
            this.arrayList.add("font/f2.ttf");
            this.arrayList.add("font/hellofont9.ttf");
            this.arrayList.add("font/hellofont13.ttf");
            this.arrayList.add("font/hellofont14.otf");
            this.arrayList.add("font/hellofont17.ttf");
            this.arrayList.add("font/hellofont18.otf");
            this.arrayList.add("font/hellofont19.ttf");

    }

    @NonNull
    @Override
    public FontAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
         View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.font,parent,false);
         return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FontAdapter.VH holder, int position) {

        Typeface typeface = Typeface.createFromAsset(context.getAssets(), arrayList.get(position));
        holder.text.setTypeface(typeface);

        holder.text.setOnClickListener(v->{
            onSelect.select(arrayList.get(position));
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class VH extends RecyclerView.ViewHolder{

        TextView text;
        public VH(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
        }
    }

    public interface OnSelect{
        void select(String path);
    }
}
