package com.example.lutrh.pkm.layout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.lutrh.pkm.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView gambar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gambar = (ImageView) findViewById(R.id.gambar);
        gambar.setOnClickListener(this);
        getSupportActionBar().hide();
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(this, HomeActivity.class));
    }
}
