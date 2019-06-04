package com.example.allamoda;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class CreateNewOutfit extends AppCompatActivity {
    private android.widget.Button button;
    private android.widget.Button button2;
    private android.widget.Button button3;

    public static ImageView hatView;
    public static ImageView shoesView;
    public static ImageView pantsView;
    public static ImageView shirtView;
    private static String TAG = "DATABASE HANDLER";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_outfit);
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        String defaultValue = null;
        String hatValue = sharedPref.getString("hat", defaultValue);
        String shirtValue = sharedPref.getString("shirt", defaultValue);
        String pantsValue = sharedPref.getString("pants", defaultValue);
        String shoesValue = sharedPref.getString("shoes", defaultValue);

        DBHandler db = new DBHandler();


        hatView = findViewById(R.id.hatPreview);
        shoesView = findViewById(R.id.ShoesPreview);
        shirtView= findViewById(R.id.ShirtPreview);
        pantsView = findViewById(R.id.PantsPreview);

        if(hatValue != null){
            db.getImage(hatValue, new DBHandler.MyCallback() {
                @Override
                public void onCallback(Bitmap value) {
                    hatView.setImageBitmap(value);
                }
            });
        }if(shirtValue != null){
            db.getImage(hatValue, new DBHandler.MyCallback() {
                @Override
                public void onCallback(Bitmap value) {
                    shirtView.setImageBitmap(value);
                }
            });
        }if(pantsValue != null){
            db.getImage(hatValue, new DBHandler.MyCallback() {
                @Override
                public void onCallback(Bitmap value) {
                    pantsView.setImageBitmap(value);
                }
            });
        }if(shoesValue != null){
            db.getImage(hatValue, new DBHandler.MyCallback() {
                @Override
                public void onCallback(Bitmap value) {
                    shoesView.setImageBitmap(value);
                }
            });
        }

        button = (Button) findViewById(R.id.button7); // Home Page
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity();
            }
        });

        button2 = (Button) findViewById(R.id.button8); // Style
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });

        button3 = (Button) findViewById(R.id.button6); // Finish
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity3();
            }
        });
    }

    public void openActivity(){
        Intent intent2 = new Intent(this, HomePage.class);
        startActivity(intent2);
    }

    public void openActivity2(){
        Intent intent2 = new Intent(this, OutfitDone.class);
        startActivity(intent2);
    }

    public void openActivity3(){
        Intent intent2 = new Intent(this, style_activity.class);
        startActivity(intent2);
    }


    @Override
    public void onResume(){
        super.onResume();

        Log.d(TAG, "onResume: RESUME!!!");
        SharedPreferences sharedPref = this.getSharedPreferences(
                "outfit", Context.MODE_PRIVATE);
        String defaultValue = null;
        String hatValue = sharedPref.getString("hat", defaultValue);
        String shirtValue = sharedPref.getString("shirt", defaultValue);
        String pantsValue = sharedPref.getString("pants", defaultValue);
        String shoesValue = sharedPref.getString("shoes", defaultValue);


        DBHandler db = new DBHandler();

        hatView = findViewById(R.id.hatPreview);
        shoesView = findViewById(R.id.ShoesPreview);
        shirtView= findViewById(R.id.ShirtPreview);
        pantsView = findViewById(R.id.PantsPreview);

        if(hatValue != null){
            Log.d(TAG, "hatValue is "+hatValue);
            db.getImage(hatValue, new DBHandler.MyCallback() {
                @Override
                public void onCallback(Bitmap value) {
                    hatView.setImageBitmap(value);
                }
            });
        }if(shirtValue != null){
            Log.d(TAG, "ShirtValue is "+shirtValue);
            db.getImage(shirtValue, new DBHandler.MyCallback() {
                @Override
                public void onCallback(Bitmap value) {
                    shirtView.setImageBitmap(value);
                }
            });
        }if(pantsValue != null){
            db.getImage(pantsValue, new DBHandler.MyCallback() {
                @Override
                public void onCallback(Bitmap value) {
                    pantsView.setImageBitmap(value);
                }
            });
        }if(shoesValue != null){
            db.getImage(shoesValue, new DBHandler.MyCallback() {
                @Override
                public void onCallback(Bitmap value) {
                    shoesView.setImageBitmap(value);
                }
            });
        }

    }

}
