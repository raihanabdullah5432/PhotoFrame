package com.photoeditor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {


    CardView cardView1, cardView2, cardView3, cardView4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        String packageName = getApplication().getPackageName();


        final DrawerLayout mDrawerLayout = findViewById(R.id.drawer);

        ImageView menu = findViewById(R.id.menu);


        menu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mDrawerLayout.openDrawer(GravityCompat.START);

            }
        });

        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.rateapps) {
                // your code

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + packageName));
                startActivity(intent);

                return true;
            }


            if (item.getItemId() == R.id.moreapps) {


                String developoerlink  = "";

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(developoerlink ));
                startActivity(intent);


            }
            if (item.getItemId() == R.id.shareapps) {


            }
            if (item.getItemId() == R.id.facebookpage) {

                String facebook_page  = "";

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(facebook_page ));
                startActivity(intent);

            }
            if (item.getItemId() == R.id.facebookgroup) {

                String facebookgroup  = "";

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(facebookgroup));
                startActivity(intent);

            }
            if (item.getItemId() == R.id.privacy) {
                String privacylink  = "";

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(privacylink));
                startActivity(intent);

            }


            return false;
        });


        cardView1 = findViewById(R.id.framelayout1);
        cardView2 = findViewById(R.id.framelayout2);
        cardView3 = findViewById(R.id.framelayout3);
        cardView4 = findViewById(R.id.framelayout4);


        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Frame.class);
                intent.putExtra("name", "frame1");
                startActivity(intent);
            }
        });
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Frame.class);
                intent.putExtra("name", "frame2");
                startActivity(intent);
            }
        });
        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Frame.class);
                intent.putExtra("name", "frame3");
                startActivity(intent);
            }
        });
        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Frame.class);
                intent.putExtra("name", "frame3");
                startActivity(intent);
            }
        });

    }

}