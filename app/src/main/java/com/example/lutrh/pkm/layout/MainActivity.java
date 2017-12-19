package com.example.lutrh.pkm.layout;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.lutrh.pkm.R;
import com.example.lutrh.pkm.helper.DatabaseHelper;
import com.example.lutrh.pkm.model.Hama;
import com.example.lutrh.pkm.model.service.UserClient;

import java.io.File;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private DatabaseHelper db;
    private ProgressBar progressBar;
    private LinearLayout linearTry;

    Retrofit.Builder builder = new Retrofit.Builder().baseUrl("https://pkm-server.herokuapp.com/").addConverterFactory(GsonConverterFactory.create());
    Retrofit retrofit = builder.build();
    UserClient userClient = retrofit.create(UserClient.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        File database = getApplicationContext().getDatabasePath("RPD.db");
        progressBar = (ProgressBar) findViewById(R.id.progress);
        linearTry = (LinearLayout) findViewById(R.id.linear_try);
        linearTry.setOnClickListener(this);

        if (!checkDataBase()) {
            db = new DatabaseHelper(this);
            fetchDatabase();
        } else {
            db = new DatabaseHelper(this);
            startActivity(new Intent(MainActivity.this, HomeActivity.class));
        }
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase("/data/data/com.example.lutrh.pkm/databases/RPD", null, SQLiteDatabase.OPEN_READONLY);
            checkDB.close();
        } catch (SQLiteException e) {
            // database doesn't exist yet.
        }
        return checkDB != null;
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    private void fetchDatabase() {
        Call<List<Hama>> call = userClient.getDatabase();

        call.enqueue(new Callback<List<Hama>>() {
            @Override
            public void onResponse(Call<List<Hama>> call, Response<List<Hama>> response) {
                if (response.isSuccessful()) {
                    for (int i = 0; i < response.body().size(); i++) {
                        db.addHama(response.body().get(i));
                    }
                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                } else {
                    Toast.makeText(MainActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Hama>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
                linearTry.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onClick(View view) {
        linearTry.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        fetchDatabase();
    }
}
