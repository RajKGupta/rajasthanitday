package govirtual360.numberverify.mainactitvities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;
import droidninja.filepicker.utils.Orientation;
import govirtual360.numberverify.R;
import govirtual360.numberverify.helper.CircleTransform;

public class DiseaseDetection extends AppCompatActivity {
    private Button upload;
    private ImageView displayPhoto;
    private ArrayList<String> filePaths = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease_detection);
        upload = (Button)findViewById(R.id.upload);
        displayPhoto = (ImageView)findViewById(R.id.displayPhoto);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filePaths.clear();
                FilePickerBuilder.getInstance().setMaxCount(1)
                        .setSelectedFiles(filePaths)
                        .enableCameraSupport(true)
                        .showGifs(false)
                        .setActivityTheme(R.style.AppTheme)
                        .setCameraPlaceholder(R.drawable.camera)
                        .withOrientation(Orientation.PORTRAIT_ONLY)
                        .pickPhoto(DiseaseDetection.this);
            }
        });


    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode)
        {
            case FilePickerConst.REQUEST_CODE_PHOTO:
                if(resultCode== Activity.RESULT_OK && data!=null)
                {
                    filePaths.clear();
                    filePaths.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_MEDIA));
                    Glide.with(DiseaseDetection.this).load(filePaths.get(0))
                            .thumbnail(0.5f)
                            .crossFade()
                            .transform(new CircleTransform(DiseaseDetection.this))
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(displayPhoto);
                    //TODO make request to the server
                }
                break;
            }

    }
}
