package com.example.lutrh.pkm.layout;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.lutrh.pkm.R;
import com.example.lutrh.pkm.adapter.HamaDitemukanAdapter;
import com.example.lutrh.pkm.adapter.HistoryAdapter;
import com.example.lutrh.pkm.helper.DatabaseHelper;
import com.example.lutrh.pkm.model.Hama;
import com.example.lutrh.pkm.model.History;

import java.util.List;

public class HamaByDitemukanActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private RecyclerView recyclerHistory;
    private RecyclerView.Adapter adapter;
    private List<Hama> mHama;
    private RelativeLayout relativeNoFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hama_by_ditemukan);
        db = new DatabaseHelper(this);

        recyclerHistory = (RecyclerView) findViewById(R.id.recycle_history);
        recyclerHistory.setHasFixedSize(true);
        recyclerHistory.setLayoutManager(new LinearLayoutManager(this));
        relativeNoFound = (RelativeLayout) findViewById(R.id.relative_no_found);


        Intent intent = getIntent();
        mHama = db.getHamaByDitemukan(intent.getExtras().getString("ditemukan"));
        adapter = new HamaDitemukanAdapter(mHama, this, intent.getExtras().getString("ditemukan"));
        recyclerHistory.setAdapter(adapter);

        if (mHama.size() == 0) {
            relativeNoFound.setVisibility(View.VISIBLE);
        }
        setTitle(intent.getExtras().getString("ditemukan").substring(0, 1).toUpperCase() + intent.getExtras().getString("ditemukan").substring(1) + " Pest");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
