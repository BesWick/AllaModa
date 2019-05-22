package com.example.allamoda;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Log;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DBHandler {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static String TAG = "DATABASE HANDLER";
// ...
    public void addShortShirt(Bitmap shirt){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //TODO: get outfit cropper to crop shoes
        Bitmap croppedShirt =  OutfitCropper.getShortShirt(shirt);
        byte[] value = BitmapUtils.getBytes(croppedShirt);
        DocumentReference shirtRef = db.collection("users").document(user.getUid());
        shirtRef.update(
                        //TODO: convert the field to a set of values
                        "shirt", FieldValue.arrayUnion(value.toString())
                );
    }
    public void addLongShirt(Bitmap shirt){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //TODO: get outfit cropper to crop shoes
        OutfitCropper.getShortShirt(shirt);
        db.collection("users").document(user.getUid())
                .update(
                        "shirt", 13
                );
    }

    public void addShortPants(Bitmap pants){

    }
    public void addLongPants(Bitmap pants){

    }

    public void addShoes(Bitmap shoes){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //TODO: get outfit cropper to crop shoes

        db.collection("users").document(user.getUid())
                .update(
                        "shoes", 13
                );
    }

    void addNewUser(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Map<String, Object> newUser = new HashMap<>();
        newUser.put("email", Objects.requireNonNull(user.getEmail()));
        db.collection("users").document(user.getUid())
                .set(newUser, SetOptions.merge());

    }





}
