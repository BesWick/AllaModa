package com.example.allamoda;

import android.database.Cursor;
import android.graphics.*;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
public class DEBUG extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);
        Button button = findViewById(R.id.debugSubmit);
        final EditText inputTitle = (EditText) findViewById(R.id.debugEditText);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = inputTitle.getText().toString();
                new DownloadImage().execute(result);
            }
        });
    }

    //AsyncTask<Input,Update,Output>
    public class DownloadImage extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... strings) {
            try{
                URL url =new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(500);
                connection.setReadTimeout(500);
                connection.connect();
                InputStream in = connection.getInputStream();
                Bitmap image = BitmapFactory.decodeStream(in);
                return image;
            }catch(MalformedURLException e) {
                e.printStackTrace();
            }catch(java.io.IOException e){
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Bitmap Bitmap){
            super.onPostExecute(Bitmap);
            if(Bitmap == null){
                Toast toast=Toast.makeText(getApplicationContext(),"Error: image not found",Toast.LENGTH_SHORT);
                toast.setMargin(50,50);
                toast.show();
                return;
            }

            ImageView image = findViewById(R.id.debugImage);
            Bitmap imageCropped = OutfitCropper.getShortShirt(Bitmap);
            image.setImageBitmap(imageCropped);


            // this is also where we would store the image in the database
        }
    }

}
