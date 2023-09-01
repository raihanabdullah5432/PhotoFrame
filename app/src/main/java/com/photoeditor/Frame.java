package com.photoeditor;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.photoeditor.Adapter.FrameAdapter;
import com.photoeditor.Adapter.FrameModel;
import com.photoeditor.Adapter.ItemModel;
import com.photoeditor.Utils.Constant;
import com.photoeditor.Utils.SaveData;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class Frame extends AppCompatActivity {



    private RecyclerView recyclerView;


    private FrameAdapter frameAdapter;
    String value;
    private List<FrameModel> list = new ArrayList();

    private String imgCover;
    private String position = "center";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame);



          value = getIntent().getStringExtra("title");

          recyclerView = findViewById(R.id.rv);

         setData();



    }


    private void setData() {

        try {


            JSONArray array = new JSONArray(new SaveData(this).getData("json"));

            for (int i=0; i < array.length(); i++){

                JSONObject object = array.getJSONObject(i);

                if (object.getString("title").equals(value)){

                    JSONArray frames = object.getJSONArray("frames");

                     for (int j = 0; j < frames.length(); j++){

                          JSONObject frame = frames.getJSONObject(j);

                         FrameModel model = new FrameModel(
                            Constant.getUrl(frame.getString("frame")),
                            frame.getString("position"),
                            frame.getString("text1"),
                            frame.getString("text2")
                         );

                         list.add(model);


                     }

                    frameAdapter = new FrameAdapter(list);

                    recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

                    recyclerView.setAdapter(frameAdapter);

                    frameAdapter.setCb(new FrameAdapter.OnClick() {
                        @Override
                        public void click(String cover,String posi, String text1, String text2) {

                            imgCover = cover;
                            position = posi;

                            CropImage.activity()
                                    .setGuidelines(CropImageView.Guidelines.ON)
                                    .start(Frame.this);


                        }
                    });


                }


            }


        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();

                Intent intent = new Intent(Frame.this,MakeFrameActivity.class);
                intent.putExtra("cover",imgCover);
                intent.putExtra("img",resultUri.getPath());
                intent.putExtra("position",position);
                startActivity(intent);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }




}