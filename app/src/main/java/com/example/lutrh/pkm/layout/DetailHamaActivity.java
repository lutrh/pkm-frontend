package com.example.lutrh.pkm.layout;

import android.content.Intent;
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
    }

    private void viewDetail() {
        getSupportActionBar().show();
        ImageView imageHama = (ImageView) findViewById(R.id.image_hama);
        TextView textHama = (TextView) findViewById(R.id.text_nama_hama);
        TextView textNamaLatin = (TextView) findViewById(R.id.text_nama_latin);
        TextView textDitemukan = (TextView) findViewById(R.id.text_ditemukan);
        TextView textDeskripsi = (TextView) findViewById(R.id.text_deskripsi);
        TextView textSolusi = (TextView) findViewById(R.id.text_solution);

        Hama hama = db.getHama(namaHama);

        textHama.setText(hama.getNama());
        textNamaLatin.setText(hama.getNamaLatin());
        textDitemukan.setText("Usually found at rice " + hama.getDitemukan());
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
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
