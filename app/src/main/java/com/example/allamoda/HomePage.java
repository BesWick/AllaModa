package com.example.allamoda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomePage extends AppCompatActivity {
    private android.widget.Button button2;
    private android.widget.Button button3;
    private android.widget.Button button4;
    private android.widget.Button button5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });

        button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity3();
            }
        });

        button4 = (Button) findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity4();
            }
        });

        button5 = (Button) findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity5();
            }
        });
    }

    public void openActivity2(){
        Intent intent2 = new Intent(this, CreateNewOutfit.class);
        startActivity(intent2);
    }

    public void openActivity3(){
        Intent intent2 = new Intent(this, StoreClothes.class);
        startActivity(intent2);
    }

    public void openActivity4(){
        Intent intent2 = new Intent(this, PreviousOutfits.class);
        startActivity(intent2);
    }

    public void openActivity5(){
        Intent intent2 = new Intent(this, SettingsPage.class);
        startActivity(intent2);
    }
}
