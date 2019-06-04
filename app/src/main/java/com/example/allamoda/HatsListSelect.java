package com.example.allamoda;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HatsListSelect extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_select);
        recyclerView = (RecyclerView) findViewById(R.id.ListSelectRecycler);

        SharedPreferences sharedPref = this. getSharedPreferences("hat", Context.MODE_PRIVATE);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DBHandler db = new DBHandler();
        db.getOutfitAll(DBHandler.HAT_OPTION, new DBHandler.ReturnCallBack() {
            @Override
            public void onCallback(List<String> value) {
                mAdapter = new hatAdapter((ArrayList<String>) value, HatsListSelect.this);
                recyclerView.setAdapter(mAdapter);
            }
        });
        // specify an adapter (see also next example)

    }
}
