package com.photoeditor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.EditText;

import com.photoeditor.Adapter.FontAdapter;

public class TextActivity extends AppCompatActivity {


    RecyclerView font;
    EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        font = findViewById(R.id.font);
        text = findViewById(R.id.text);
        FontAdapter fontAdapter = new FontAdapter(new FontAdapter.OnSelect() {
            @Override
            public void select(String path) {
                Typeface typeface = Typeface.createFromAsset(getAssets(),path);
                text.setTypeface(typeface);
            }
        });
        font.setLayoutManager(new GridLayoutManager(this,3));
        font.setAdapter(fontAdapter);

    }
}