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

public class PreviousOutfitAdapter extends RecyclerView.Adapter<PreviousOutfitAdapter.MyViewHolder> {
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

            image =  v.findViewById(R.id.imagePrevious);
        }
    }

    public PreviousOutfitAdapter(ArrayList<String> givenTitle, Context context){

        this.mInflater = LayoutInflater.from(context);
        title = givenTitle;
        hatContext = context;
    }
    @Override
    public PreviousOutfitAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = mInflater.inflate(R.layout.previous_outfit_layout, viewGroup, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final PreviousOutfitAdapter.MyViewHolder myViewHolder, final int i) {
        Log.d(TAG, "onBindViewHolder: titleView" + title.get(i));
        DBHandler db = new DBHandler();
        db.getImage(title.get(i), new DBHandler.MyCallback() {
            @Override
            public void onCallback(final Bitmap value) {
                myViewHolder.image.setImageBitmap(value);
            }
        });

    }

    @Override
    public int getItemCount() {
        return title.size();
    }
}
