package com.example.allamoda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OutfitDone extends AppCompatActivity {
    private android.widget.Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outfit_done);

        button = (Button) findViewById(R.id.button12); // Home Page
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity();
            }
        });
    }

    public void openActivity(){
        Intent intent2 = new Intent(this, HomePage.class);
        startActivity(intent2);
    }
}
