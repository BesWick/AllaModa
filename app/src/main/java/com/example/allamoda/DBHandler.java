package com.example.allamoda;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DBHandler {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static String TAG = "DATABASE HANDLER";
    FirebaseStorage storage = FirebaseStorage.getInstance();

    // ...
    public void addShortShirt(String shirt){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //TODO: get outfit cropper to crop shoes
        DocumentReference shirtRef = db.collection("users").document(user.getUid());
        shirtRef.update(
                        //TODO: convert the field to a set of values
                        "shirt", FieldValue.arrayUnion(shirt)
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
    //stores image bitmap to Firebase Storage then stores id through firestore
    public void addImage(Bitmap bInput){

        //flip image upside down
        Matrix matrix = new Matrix();
        matrix.preScale(1.0f, -1.0f);
        Bitmap bitmap = Bitmap.createBitmap(bInput, 0, 0, bInput.getWidth(), bInput.getHeight(), matrix, true);

        //cop image to tshirt
        Bitmap shirt = OutfitCropper.getShortShirt(bitmap);

        //Store image into firebase storage
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        StorageReference storageRef = storage.getReference();
        StorageReference imageRef = storageRef.child(timeStamp+".jpg");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        shirt.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = imageRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
                String id = taskSnapshot.getMetadata().getName().toString();
                addShortShirt(id);
            }
        });
    }

    //this get the image bitmap based on the given path of the image
    public Bitmap getImage(String imageName){
        // Create a storage reference from our app
        StorageReference storageRef = storage.getReference();
        final Bitmap[] out = new Bitmap[1];
        // Create a reference with an initial file path and name
        StorageReference pathReference = storageRef.child(imageName);

        final long ONE_MEGABYTE = 1024 * 1024;
        pathReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Data for "images/island.jpg" is returns, use this as needed
                out[0] = BitmapUtils.getImage(bytes);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors

            }
        });

        return out[0];

    }



}
