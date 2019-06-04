package com.example.allamoda;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

public class OutfitDone extends AppCompatActivity {
    private static final String TAG = "OUTFITDONE";
    private android.widget.Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DBHandler db = new DBHandler();

        SharedPreferences sharedPref = this.getSharedPreferences(
                "outfit", Context.MODE_PRIVATE);
        String defaultValue = null;
        String hatValue = sharedPref.getString("hat", defaultValue);
        String shirtValue = sharedPref.getString("shirt", defaultValue);
        String pantsValue = sharedPref.getString("pants", defaultValue);
        String shoesValue = sharedPref.getString("shoes", defaultValue);

        Log.d(TAG, "onCreate: hat "+ hatValue);

        setContentView(R.layout.activity_outfit_done);
        final ImageView hat = findViewById(R.id.hatFin);
        final ImageView shirt = findViewById(R.id.shirtFin);
        final ImageView pants = findViewById(R.id.pantsFin);
        final ImageView shoes = findViewById(R.id.shoesFin);

        if(hatValue != null){
            db.getImage(hatValue, new DBHandler.MyCallback() {
                @Override
                public void onCallback(Bitmap value) {
                    hat.setImageBitmap(value);
                }
            });
        }if(shirtValue != null){
            db.getImage(shirtValue, new DBHandler.MyCallback() {
                @Override
                public void onCallback(Bitmap value) {
                    shirt.setImageBitmap(value);
                }
            });
        }if(pantsValue != null){
            db.getImage(pantsValue, new DBHandler.MyCallback() {
                @Override
                public void onCallback(Bitmap value) {
                    pants.setImageBitmap(value);
                }
            });
        }if(shoesValue != null){
            db.getImage(shoesValue, new DBHandler.MyCallback() {
                @Override
                public void onCallback(Bitmap value) {
                    shoes.setImageBitmap(value);
                }
            });
        }

        button = (Button) findViewById(R.id.button12); // Home Page
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity();
            }
        });
        Button saveButton = (Button) findViewById(R.id.saveOutfit); // Home Page
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHandler db = new DBHandler();
                LinearLayout linearLayout = findViewById(R.id.finishedOutfit);
                Bitmap save = loadBitmapFromView(linearLayout, 400, 1600);

                db.addImage(DBHandler.OUTFIT_OPTION, save, new DBHandler.MyCallback() {
                    @Override
                    public void onCallback(Bitmap value) {

                    }
                });
            }
        });

    }

    public void openActivity(){
        Intent intent2 = new Intent(this, HomePage.class);
        startActivity(intent2);
    }
    private void takeScreenshot() {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".png";

            // create bitmap screen capture
            View v1 = findViewById(R.id.finishedOutfit);
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.PNG, quality, outputStream);

            DBHandler db = new DBHandler();
            db.addImage(DBHandler.OUTFIT_OPTION, bitmap, new DBHandler.MyCallback() {
                @Override
                public void onCallback(Bitmap value) {

                }
            });
            outputStream.flush();
            outputStream.close();

        } catch (Throwable e) {
            // Several error may come out with file handling or DOM
            e.printStackTrace();
        }
    }

    public static Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width , height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.layout(0, 0, v.getLayoutParams().width, v.getLayoutParams().height);
        v.draw(c);
        return b;
    }
}
