package com.example.allamoda;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class shorts_list extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shorts_list);
        recyclerView = (RecyclerView) findViewById(R.id.shortz);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DBHandler db = new DBHandler();
        db.getOutfitAll(DBHandler.SHORT_PANTS_OPTION, new DBHandler.ReturnCallBack() {
            @Override
            public void onCallback(List<String> value) {
                mAdapter = new hatAdapter((ArrayList<String>) value, shorts_list.this);
                recyclerView.setAdapter(mAdapter);
            }
        });
        // specify an adapter (see also next example)

    }
}
