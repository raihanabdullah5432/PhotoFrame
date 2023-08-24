package com.photoeditor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.photoeditor.Adapter.FrameAdapter;

import java.util.ArrayList;
import java.util.List;

public class Frame extends AppCompatActivity {



    protected RecyclerView recyclerView;


    private FrameAdapter frameAdapter;
    private List list = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame);



        String value = getIntent().getStringExtra("name");

        Toast.makeText(this, "" +value, Toast.LENGTH_SHORT).show();

        list.add(R.drawable.birthday);
        list.add(R.drawable.birthday);
        list.add(R.drawable.birthday);
        list.add(R.drawable.birthday);
        list.add(R.drawable.birthday);
        list.add(R.drawable.birthday);
        list.add(R.drawable.birthday);
        list.add(R.drawable.birthday);
        list.add(R.drawable.birthday);
        list.add(R.drawable.birthday);
        list.add(R.drawable.birthday);
        list.add(R.drawable.birthday);
        list.add(R.drawable.birthday);
        list.add(R.drawable.birthday);
        list.add(R.drawable.birthday);
        list.add(R.drawable.birthday);
        list.add(R.drawable.birthday);
        list.add(R.drawable.birthday);
        list.add(R.drawable.birthday);
        list.add(R.drawable.birthday);



        recyclerView = findViewById(R.id.rv);


        frameAdapter = new FrameAdapter(list);



        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        recyclerView.setAdapter(frameAdapter);



    }
}