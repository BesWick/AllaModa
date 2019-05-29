package com.example.allamoda;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.annotation.NonNull;
import android.util.Log;
import com.google.android.gms.tasks.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.*;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import javax.security.auth.callback.Callback;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class DBHandler {
    public interface MyCallback {
        void onCallback(Bitmap value);
    }
    public interface ReturnCallBack {
        void onCallback(List<String> value);
    }


    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static String TAG = "DATABASE HANDLER";
    private FirebaseStorage storage = FirebaseStorage.getInstance();

    // ...
    private void addShortShirt(String shirt){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //TODO: get outfit cropper to crop shoes
        assert user != null;
        DocumentReference shirtRef = db.collection("users").document(user.getUid());
        shirtRef.update(
                        //TODO: convert the field to a set of values
                        "shirt", FieldValue.arrayUnion(shirt)
                );
    }

    private void addShortPants(Bitmap pants){

    }
    private void addLongPants(Bitmap pants){

    }

    private void addShoes(Bitmap shoes){
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
                Log.d(TAG, "onSuccess: ID IS"+ id);
                addShortShirt(id);
            }
        });
    }

    //this get the image bitmap based on the given path of the image
    public Bitmap getImage(String imageName, final MyCallback callBack){
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
                //callback to return the value
                callBack.onCallback(out[0]);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                Log.d(TAG, "onFailure: GETTING IMAGE FAILURE");
            }
        });
        return out[0];
    }


    //TODO: add function which returns a bitmap list of certain clothing
    public List<Bitmap> getAllImages(final ReturnCallBack callBack){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final List<Bitmap> imageList = new ArrayList<>();



        DocumentReference docRef = db.collection("users").document(user.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        final List<String> group = (List<String>) document.get("shirt");
                        assert group != null;
                        callBack.onCallback(group);

                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
        return imageList;
    }



}