package com.example.allamoda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CreateNewOutfit extends AppCompatActivity {
    private android.widget.Button button;
    private android.widget.Button button2;
    private android.widget.Button button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_outfit);

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
}