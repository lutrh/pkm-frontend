package com.example.lutrh.pkm.layout;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lutrh.pkm.R;
import com.example.lutrh.pkm.helper.DatabaseHelper;
import com.example.lutrh.pkm.model.Hama;
import com.example.lutrh.pkm.model.History;
import com.example.lutrh.pkm.model.ResponseApi;

public class DetailHamaActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private String namaHama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_hama);
        db = new DatabaseHelper(this);
        Intent intent = getIntent();
        namaHama = intent.getExtras().getString("nama_hama");
        viewDetail();

        setTitle(namaHama.substring(0, 1).toUpperCase() + namaHama.substring(1));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void viewDetail() {
        ImageView imageHama = (ImageView) findViewById(R.id.image_hama);
        ImageView imageDitemukan = (ImageView) findViewById(R.id.image_ditemukan);
        TextView textHama = (TextView) findViewById(R.id.text_nama_hama);
        TextView textNamaLatin = (TextView) findViewById(R.id.text_nama_latin);
        TextView textDitemukan = (TextView) findViewById(R.id.text_ditemukan);
        TextView textDeskripsi = (TextView) findViewById(R.id.text_deskripsi);
        TextView textSolusi = (TextView) findViewById(R.id.text_solution);

        Hama hama = db.getHama(namaHama);


        String tempat = "";
        switch (hama.getDitemukan()) {
            case "leaves":
                tempat = "daun";
                break;
            case "log":
                tempat = "batang";
                break;
            case "water":
                tempat = "air";
                break;
        }

        textHama.setText(hama.getNama().substring(0, 1).toUpperCase() + hama.getNama().substring(1));
        textNamaLatin.setText(hama.getNamaLatin());
        textDitemukan.setText("Ditemukan di " + tempat);
        textDeskripsi.setText(hama.getDeskripsi());
        textSolusi.setText(hama.getSolusi());

        switch (hama.getNama()) {
            case "wereng":
                imageHama.setImageResource(R.drawable.ig_wereng);
                break;
            case "belalang":
                imageHama.setImageResource(R.drawable.ig_belalang);
                break;
            case "tikus sawah":
                imageHama.setImageResource(R.drawable.ig_tikus);
                break;
            case "walang sangit":
                imageHama.setImageResource(R.drawable.ig_walang_sangit);
                break;
        }

        switch (hama.getDitemukan()) {
            case "leaves":
                imageDitemukan.setImageResource(R.drawable.ic_leafs);
                break;
            case "log":
                imageDitemukan.setImageResource(R.drawable.ic_log);
                break;
            case "water":
                imageDitemukan.setImageResource(R.drawable.ic_water);
                break;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
