package com.example.lutrh.pkm.layout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lutrh.pkm.R;
import com.example.lutrh.pkm.adapter.CuacaAdapter;
import com.example.lutrh.pkm.adapter.HistoryAdapter;
import com.example.lutrh.pkm.model.List;
import com.example.lutrh.pkm.model.WeatherApi;
import com.example.lutrh.pkm.model.WeatherDetail;
import com.example.lutrh.pkm.model.service.UserClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CuacaActivity extends AppCompatActivity {

    Retrofit.Builder builder = new Retrofit.Builder().baseUrl("https://api.openweathermap.org/data/2.5/").addConverterFactory(GsonConverterFactory.create());
    Retrofit retrofit = builder.build();
    UserClient userClient = retrofit.create(UserClient.class);

    private RelativeLayout linearFetch;
    private java.util.List<com.example.lutrh.pkm.model.List> mCuaca;
    private RecyclerView recyclerCuaca;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuaca);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Prediksi Cuaca");
        linearFetch = (RelativeLayout) findViewById(R.id.linear_fetch);
        getForecast();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void getForecast() {
        recyclerCuaca = (RecyclerView) findViewById(R.id.recycler_cuaca);
        recyclerCuaca.setHasFixedSize(true);
        recyclerCuaca.setLayoutManager(new LinearLayoutManager(this));

        String latitude = getIntent().getStringExtra("lat");
        String longitude = getIntent().getStringExtra("long");
        String units = "metric";
        String appid = "ece1a8cdc266e5c942e10bc8c21a3f50";

        Call<WeatherDetail> call = userClient.getForecast(latitude, longitude, units, appid);

        call.enqueue(new Callback<WeatherDetail>() {
            @Override
            public void onResponse(Call<WeatherDetail> call, Response<WeatherDetail> response) {
                adapter = new CuacaAdapter(response.body().getList(), CuacaActivity.this, response.body());
                recyclerCuaca.setAdapter(adapter);
                linearFetch.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<WeatherDetail> call, Throwable t) {
                Toast.makeText(CuacaActivity.this, t.toString(), Toast.LENGTH_LONG).show();
                Log.d("qweasd", t.toString());
            }
        });
    }
}
