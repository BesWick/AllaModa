package com.example.allamoda;

import android.support.annotation.NonNull;
import android.util.Log;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DBHandler {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static String TAG = "DATABASE HANDLER";
// ...
    public static void addShirt(){

    }

    public static void addPants(){

    }

    public static void addShoes(){

    }

    public void addNewUser(FirebaseUser user){
        Map<String, Object> newUser = new HashMap<>();
        newUser.put("email", Objects.requireNonNull(user.getEmail()));
        db.collection("users").document(user.getUid())
                .set(newUser, SetOptions.merge());

    }


}
