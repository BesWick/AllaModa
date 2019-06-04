package com.example.allamoda;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class DEBUG_TAKE_PICTURE extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static String TAG = "DATABASE HANDLER";

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
            final Bitmap bInput = (Bitmap) extras.get("data");
            final ImageView imageView = findViewById(R.id.DEBUG_IMAGE_VIEW);


            //THis code is to store the shirt into db and get it from db
            // and display shirt image
            final DBHandler dbHandler = new DBHandler();
            dbHandler.addImage(DBHandler.SHORT_PANTS_OPTION, bInput, new DBHandler.MyCallback() {
                @Override
                public void onCallback(Bitmap value) {
                    imageView.setImageBitmap(value);
                }
            });
        }
    }
}
