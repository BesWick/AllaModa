package com.example.allamoda;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button btnCapture;
    private ImageView imgCapture;
    Camera camera;


    private static final int REQ_CAMERA_IMAGE = 123;
    private static final int Image_Capture_Code = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // here, Permission is not granted
            ActivityCompat.requestPermissions(this, new String[] {android.Manifest.permission.CAMERA}, 50);
        }


        String message = "Click the button below to start";
        if(cameraNotDetected()){
            message = "No camera detected, clicking the button below will have unexpected behaviour.";
        }
        TextView cameraDescriptionTextView = (TextView) findViewById(R.id.text_view_camera_description);
        cameraDescriptionTextView.setText(message);
    }




private boolean cameraNotDetected() {
        return !getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
        }


    public void onUseCameraClick(View button){
        Intent intent = new Intent(this, CameraActivity.class);
        startActivityForResult(intent, REQ_CAMERA_IMAGE);
    }




@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        byte [] bp;
        Bitmap bmp;

        if (requestCode == REQ_CAMERA_IMAGE) {
            if (resultCode == RESULT_OK) {
                bp =  data.getByteArrayExtra("data");
                bmp = BitmapFactory.decodeByteArray(bp, 0, bp.length);
                imgCapture = findViewById(R.id.image_view_captured_image);
                imgCapture.setImageBitmap(bmp);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            }
        }
    }







}