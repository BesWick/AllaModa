package com.example.allamoda;


import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class shoesAdapter extends RecyclerView.Adapter<shoesAdapter.MyViewHolder> {
    private static final String TAG = "default ";
    @NonNull
    private LayoutInflater mInflater;

    private ArrayList<String> title;

    private Context hatContext;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public Button textView;
        public ImageView image;
        public MyViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.hatButton);
            image =  v.findViewById(R.id.hatImage);
        }
    }

    public shoesAdapter (ArrayList<String> givenTitle, Context context){

        this.mInflater = LayoutInflater.from(context);
        title = givenTitle;
        hatContext = context;
    }
    @Override
    public shoesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = mInflater.inflate(R.layout.hat_layout, viewGroup, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final shoesAdapter.MyViewHolder myViewHolder, final int i) {
        Log.d(TAG, "onBindViewHolder: titleView" + title.get(i));
        DBHandler db = new DBHandler();
        db.getImage(title.get(i), new DBHandler.MyCallback() {
            @Override
            public void onCallback(final Bitmap value) {
                myViewHolder.image.setImageBitmap(value);
                myViewHolder.textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences sharedPref = hatContext.getSharedPreferences(
                                "outfit", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("shoes", title.get(i));
                        editor.commit();


                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return title.size();
    }
}
