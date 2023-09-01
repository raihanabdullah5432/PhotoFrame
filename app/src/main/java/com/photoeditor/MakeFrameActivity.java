package com.photoeditor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.photoeditor.Utils.Utils;
import com.photoeditor.Utils.ZoomView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;
import java.io.OutputStream;

import us.technerd.tnimageview.TNImageView;


public class MakeFrameActivity extends AppCompatActivity {

    private ImageView frame;
    private ImageView myZoomageView;
    private FrameLayout main_frame;
    private RelativeLayout holder;
    LinearLayout gallery;
    LinearLayout save;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_frame);
        frame = findViewById(R.id.frame);
        myZoomageView = findViewById(R.id.imageView);
        main_frame = findViewById(R.id.main_frame);
        holder = findViewById(R.id.holder);


        gallery = findViewById(R.id.gallery);
        save = findViewById(R.id.save);

        String cover = getIntent().getStringExtra("cover");
        String img = getIntent().getStringExtra("img");
        String position = getIntent().getStringExtra("position");

        Glide.with(this).load(cover).addListener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, @Nullable Object model, @NonNull Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(@NonNull Drawable resource, @NonNull Object model, Target<Drawable> target, @NonNull DataSource dataSource, boolean isFirstResource) {

                float w = resource.getIntrinsicWidth();
                float h = resource.getIntrinsicHeight();


                main_frame.getLayoutParams().width = (int) w;
                main_frame.getLayoutParams().height = (int) h;
                main_frame.requestLayout();

                ZoomView zoomView = new ZoomView();
                zoomView.makeRotatableScalable(myZoomageView);

                Glide.with(MakeFrameActivity.this).load(img).into(myZoomageView);

                if (position.equals("right")){
                     myZoomageView.setScaleType(ImageView.ScaleType.FIT_END);
                }
                else  if (position.equals("left")){
                    myZoomageView.setScaleType(ImageView.ScaleType.FIT_START);
                }else {
                    myZoomageView.setScaleType(ImageView.ScaleType.CENTER);

                }


                return false;
            }
        }).into(frame);


        gallery.setOnClickListener(v->{
            gallery();
        });

        save.setOnClickListener(v->{

            save();
        });


        findViewById(R.id.font).setOnClickListener(v->{
            Intent intent = new Intent(MakeFrameActivity.this, TextActivity.class);
            startActivity(intent);

        });


    }

    void position(String po,float w, float h){

         if (po.equals("right")){

             RelativeLayout.LayoutParams layoutParams =
                     (RelativeLayout.LayoutParams)myZoomageView.getLayoutParams();
             layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
             myZoomageView.setLayoutParams(layoutParams);
             myZoomageView.requestLayout();

         }

        if (po.equals("left")){

            myZoomageView.animate().x(0).setDuration(0).start();

        }

        if (po.equals("top")){

            myZoomageView.animate().y(0).setDuration(0).start();

        }
        if (po.equals("bottom")){

            myZoomageView.animate().y(main_frame.getHeight()-myZoomageView.getHeight()).setDuration(0).start();

        }



    }

    private void save() {

        if(Build.VERSION.SDK_INT < 33){

            if (checkPermission()){
                Bitmap bitmap = getBitmapFromTextView(main_frame);
                saveBitmapToGallery(bitmap);
            }else {
                requestPermission();

            }

        }else {

            Bitmap bitmap = getBitmapFromTextView(main_frame);
            saveBitmapToGallery(bitmap);
        }



    }

    private Bitmap getBitmapFromTextView(FrameLayout textView) {
        Bitmap bitmap = Bitmap.createBitmap(textView.getWidth(), textView.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        textView.layout(textView.getLeft(), textView.getTop(), textView.getRight(), textView.getBottom());
        textView.draw(canvas);
        return bitmap;
    }

    private void saveBitmapToGallery(Bitmap bitmap) {
        String filename = "TextViewImage_" + System.currentTimeMillis() + ".png";
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DISPLAY_NAME, filename);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
        values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000);
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());

        Uri imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        try {
            OutputStream out = getContentResolver().openOutputStream(imageUri);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.close();
            Toast.makeText(this, "Image saved to gallery", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to save image", Toast.LENGTH_SHORT).show();
        }
    }





    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(MakeFrameActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(MakeFrameActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(MakeFrameActivity.this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(MakeFrameActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 203);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 203:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Bitmap bitmap = getBitmapFromTextView(main_frame);
                    saveBitmapToGallery(bitmap);
                } else {
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                }
                break;
        }
    }



    void gallery(){

        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                Glide.with(MakeFrameActivity.this).load(resultUri).into(myZoomageView);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }



}