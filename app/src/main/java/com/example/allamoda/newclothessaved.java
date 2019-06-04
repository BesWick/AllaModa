package com.example.allamoda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class newclothessaved extends AppCompatActivity {
    private android.widget.Button button;
    private android.widget.Button button2;
    private android.widget.Button button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newclothessaved);
        button = (Button) findViewById(R.id.button13);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity();
            }
        });
        button2 = (Button) findViewById(R.id.button14);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });
        button3 = (Button) findViewById(R.id.button14);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity3();
            }
        });
    }
    public void openActivity(){
        Intent intent2 = new Intent(this, CreateNewOutfit.class);
        startActivity(intent2);
    }
    public void openActivity2(){
        Intent intent2 = new Intent(this, StoreClothes.class);
        startActivity(intent2);
    }
    public void openActivity3(){
        Intent intent2 = new Intent(this, HomePage.class);
        startActivity(intent2);
    }
}

