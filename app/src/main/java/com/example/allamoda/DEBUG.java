package com.example.allamoda;

import android.database.Cursor;
import android.graphics.*;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;



class tagHandler{
    public String TAG = "value tag";

    public void setView(List<Bitmap> val, ImageView imageView){
        Log.d(TAG, "onCallback: value Size" + val.size());
        imageView.setImageBitmap(val.get(val.size()-1));

    }

}
public class DEBUG extends AppCompatActivity {


    private String TAG = "value tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);
        final DBHandler dbHandler = new DBHandler();
        final ImageView imageView = findViewById(R.id.debugImage);
        dbHandler.getAllImages(new DBHandler.ReturnCallBack() {
            @Override
            public void onCallback(List<String> value) {
                dbHandler.getImage(value.get(value.size() - 1), new DBHandler.MyCallback() {
                    @Override
                    public void onCallback(Bitmap value) {
                        imageView.setImageBitmap(value);
                    }
                });
            }
        });


    }

}
