package com.example.allamoda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;


import java.nio.file.Path;
import java.util.Arrays;

public class MainActivity1 extends AppCompatActivity {
    private static final int RC_SIGN_IN = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        FirebaseAuth auth = FirebaseAuth.getInstance();


        Button debugActivityButton = findViewById(R.id.debugActivityButton);
        final Intent debugActivity = new Intent(this,DEBUG.class);
        debugActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(debugActivity);
            }
        });
        Button debugImageButton = findViewById(R.id.debugImage);
        final Intent debugImageActivity = new Intent(this,DEBUG_TAKE_PICTURE.class);
        debugImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(debugImageActivity);
            }
        });

        Button debugPants= findViewById(R.id.debugPants);
        final Intent debugPantsActivity = new Intent(this,PANTS_DEBUG.class);
        debugPants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(debugPantsActivity);
            }
        });
        if (auth.getCurrentUser() != null) {
            // already signed in

        } else {
            // not signed in
            startActivityForResult(
                    // Get an instance of AuthUI based on the default app
                    AuthUI.getInstance().createSignInIntentBuilder()
                            .setAvailableProviders(Arrays.asList(
                    new AuthUI.IdpConfig.GoogleBuilder().build()))
                            .build(),
                    RC_SIGN_IN);


        }


    }



}
