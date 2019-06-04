package com.example.allamoda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class style_activity extends AppCompatActivity {
    private android.widget.Button button; // homepage 16
    private android.widget.Button button2; // select 17
    private android.widget.Button button3; // hats 9
    private android.widget.Button button4; // pants 11
    private android.widget.Button button5; // long sleeve shirt 10
    private android.widget.Button button6; // shoes 18
    private android.widget.Button button7; // shorts 19
    private android.widget.Button button8; // t shirt 20

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_style_activity);

        button = (Button) findViewById(R.id.button16); // Home Page
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity();
            }
        });



        button3 = (Button) findViewById(R.id.button9); // hats
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity3();
            }
        });

        button4 = (Button) findViewById(R.id.button11); // pants
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity4();
            }
        });

        button5 = (Button) findViewById(R.id.button10); // long sleeve shirts
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity5();
            }
        });

        button6 = (Button) findViewById(R.id.button18); // shoes
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity6();
            }
        });

        button7 = (Button) findViewById(R.id.button19); // shorts
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity7();
            }
        });

        button8 = (Button) findViewById(R.id.button20); // t shirt
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity8();
            }
        });
    }

    public void openActivity(){
        Intent intent2 = new Intent(this, HomePage.class);
        startActivity(intent2);
    }



    public void openActivity3(){
        Intent intent2 = new Intent(this, hatsList.class);
        startActivity(intent2);
    }

    public void openActivity4(){
        Intent intent2 = new Intent(this, long_pants_activity.class);
        startActivity(intent2);
    }

    public void openActivity5(){
        Intent intent2 = new Intent(this, long_sleeve_shirt_list.class);
        startActivity(intent2);
    }

    public void openActivity6(){
        Intent intent2 = new Intent(this, shoes_list.class);
        startActivity(intent2);
    }

    public void openActivity7(){
        Intent intent2 = new Intent(this, shorts_list.class);
        startActivity(intent2);
    }

    public void openActivity8(){
        Intent intent2 = new Intent(this, T_Shirt_list.class);
        startActivity(intent2);
    }
}
