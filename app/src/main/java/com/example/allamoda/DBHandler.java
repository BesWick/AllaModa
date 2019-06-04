package com.example.allamoda;

import android.graphics.Bitmap;
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

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

public class DBHandler {

    public static int SHIRT_OPTION = 0;
    public static int PANTS_OPTION = 1;
    public static int SHOES_OPTION = 2;
    public static int HAT_OPTION = 3;
    public static int OUTFIT_OPTION = 4;
    public static int LONG_SHIRT_OPTION = 5;
    public static int SHORT_PANTS_OPTION = 6;



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
    private void addShirt(String shirt){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //TODO: get outfit cropper to crop shoes
        assert user != null;
        DocumentReference shirtRef = db.collection("users").document(user.getUid());
        shirtRef.update(
                        //TODO: convert the field to a set of values
                        "shirt", FieldValue.arrayUnion(shirt)
                );
    }

    private void addPants(String pants){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //TODO: get outfit cropper to crop shoes
        assert user != null;
        DocumentReference shirtRef = db.collection("users").document(user.getUid());
        shirtRef.update(
                //TODO: convert the field to a set of values
                "pants", FieldValue.arrayUnion(pants)
        );

    }
    private void addHats(String pants){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //TODO: get outfit cropper to crop shoes
        assert user != null;
        DocumentReference shirtRef = db.collection("users").document(user.getUid());
        shirtRef.update(
                //TODO: convert the field to a set of values
                "hats", FieldValue.arrayUnion(pants)
        );

    }


    private void addShoes(String shoes){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //TODO: get outfit cropper to crop shoes
        assert user != null;
        DocumentReference shirtRef = db.collection("users").document(user.getUid());
        shirtRef.update(
                //TODO: convert the field to a set of values
                "shoes", FieldValue.arrayUnion(shoes)
        );
    }
    private void addOutfit(String outfit){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //TODO: get outfit cropper to crop shoes
        assert user != null;
        DocumentReference shirtRef = db.collection("users").document(user.getUid());
        shirtRef.update(
                //TODO: convert the field to a set of values
                "outfit", FieldValue.arrayUnion(outfit)
        );
    }
    private void addHat(String hat){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //TODO: get outfit cropper to crop shoes
        assert user != null;
        DocumentReference shirtRef = db.collection("users").document(user.getUid());
        shirtRef.update(
                //TODO: convert the field to a set of values
                "shoes", FieldValue.arrayUnion(hat)
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
    public void addImage(final int option, Bitmap bInput, final MyCallback callback){
        final Bitmap shirt;
        //flip image upside down
        Matrix matrix = new Matrix();
        matrix.preScale(1.0f, -1.0f);
        Bitmap bitmap = Bitmap.createBitmap(bInput, 0, 0, bInput.getWidth(), bInput.getHeight(), matrix, true);

        //cop image to tshirt
        switch (option){
            case 0:
                shirt = OutfitCropper.getShortShirt(bitmap);
                break;
            case 1:
                shirt = OutfitCropper.getPant(bitmap);
                break;
            case 2:
                shirt = OutfitCropper.getShoes(bitmap);
                break;
            case 3:
                shirt = OutfitCropper.getHat(bitmap);
                break;
            case 4:
                shirt = bitmap;
                break;
            case 5:
                shirt = OutfitCropper.getLongShirt(bitmap);
                break;
            default:
                shirt = OutfitCropper.getLongPants(bitmap);
                break;

        }

        //Store image into firebase storage
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        StorageReference storageRef = storage.getReference();
        StorageReference imageRef = storageRef.child(timeStamp+".png");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        shirt.compress(Bitmap.CompressFormat.PNG, 100, baos);
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

                //this switch is used to determine the category of the item
                switch (option){
                    case 0:
                        addShirt(id);
                        break;
                    case 1:
                        addPants(id);
                        break;
                    case 2:
                        addShoes(id);
                        break;
                    case 3:
                        addHat(id);
                        break;
                    case 4:
                        addOutfit(id);
                        break;
                    case 5:
                        addShirt(id);
                    case 6:
                        addPants(id);
                }
                callback.onCallback(shirt);
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
    public List<Bitmap> getOutfitAll(final int option, final ReturnCallBack callBack){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final List<Bitmap> imageList = new ArrayList<>();


        DocumentReference docRef = db.collection("users").document(user.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                String outfitOption = null;
                switch (option){
                    case 0:
                        outfitOption = "shirt";
                        break;
                    case 1:
                        outfitOption = "pants";
                        break;
                    case 2:
                        outfitOption = "shoes";
                        break;
                    case 3:
                        outfitOption = "hat";
                        break;
                    case 4:
                        outfitOption = "outfit";
                        break;
                }

                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        final List<String> group = (List<String>) document.get(outfitOption);
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



    public List<String> getOutfitByEmail(String email, int option, final ReturnCallBack callBack){
        //get user outfit byt email
        List<String> outfitList = new ArrayList<>();
        String outfitOption = null;
        switch (option){
            case 0:
                outfitOption = "shirt";
                break;
            case 1:
                outfitOption = "pants";
                break;
            case 2:
                outfitOption = "shoes";
                break;
            case 3:
                outfitOption = "hat";
                break;
            case 4:
                outfitOption = "outfit";
                break;
        }
        final String finalOutfitOption = outfitOption;
        db.collection("users").whereEqualTo("email", email).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<String> outfitList = (List<String>) queryDocumentSnapshots.getDocuments().get(0).get(finalOutfitOption);
                callBack.onCallback(outfitList);
                Log.d(TAG, "onSuccess: OUTFITLIST SIZE" + outfitList.size());
            }
        });
        return null;
    }




}