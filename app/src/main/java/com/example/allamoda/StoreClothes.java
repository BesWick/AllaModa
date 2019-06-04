package com.example.allamoda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StoreClothes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_clothes);

        Button storeShorts = findViewById(R.id.storeShorts);
        final Intent intent2 = new Intent(this, ShortsListSelect.class);

        storeShorts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent2);
            }
        });
        Button storePants = findViewById(R.id.storePants);
        final Intent pantsIntent = new Intent(this, long_pants_activity.class);

        storePants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(pantsIntent);
            }
        });

        Button storeLongShirt = findViewById(R.id.storeLongShirt);
        final Intent longShirtIntent = new Intent(this, long_sleeve_shirt_list.class);

        storeLongShirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(longShirtIntent);
            }
        });
        Button storeShortShirt = findViewById(R.id.storeShortShirt);
        final Intent shortShirtIntent = new Intent(this, T_Shirt_list.class);

        storeShortShirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(shortShirtIntent);
            }
        });
    }
}
