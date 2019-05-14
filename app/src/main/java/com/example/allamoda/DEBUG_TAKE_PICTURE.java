package com.example.allamoda;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class DEBUG_TAKE_PICTURE extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug__take__picture);
        dispatchTakePictureIntent();


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap bInput = (Bitmap) extras.get("data");
            ImageView imageView = findViewById(R.id.DEBUG_IMAGE_VIEW);
            Bitmap  bOutput;
            Matrix matrix = new Matrix();
            matrix.preScale(1.0f, -1.0f);
            bOutput = Bitmap.createBitmap(bInput, 0, 0, bInput.getWidth(), bInput.getHeight(), matrix, true);
            Bitmap shirt = OutfitCropper.getShortShirt(bOutput);

            imageView.setImageBitmap(shirt);

        }
    }
}
