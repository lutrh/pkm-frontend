package com.example.lutrh.pkm.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lutrh.pkm.R;
import com.example.lutrh.pkm.layout.CuacaActivity;
import com.example.lutrh.pkm.layout.HamaByDitemukanActivity;
import com.example.lutrh.pkm.layout.HomeActivity;
import com.example.lutrh.pkm.model.WeatherApi;
import com.example.lutrh.pkm.model.service.UserClient;

import im.delight.android.location.SimpleLocation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.lutrh.pkm.R.id.container;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;
    private SimpleLocation location;
    private TextView textNamaKota, textSuhu, textLoading;
    private int suhu;
    private String kota, main, longitude, latitude;
    private CardView weatherPanel, loadingPanel;
    private ImageView mainWeather;
    private LinearLayout linearLeaves, linearLog, linearWater, linearCuaca;
    private ProgressBar progressBar;

    Retrofit.Builder builder = new Retrofit.Builder().baseUrl("https://api.openweathermap.org/data/2.5/").addConverterFactory(GsonConverterFactory.create());
    Retrofit retrofit = builder.build();
    UserClient userClient = retrofit.create(UserClient.class);

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        location = new SimpleLocation(getActivity());

        if (!location.hasLocationEnabled()) {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Location Permission")
                    .setMessage("Protector need access to your location")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            SimpleLocation.openSettings(getActivity());
                        }
                    })
                    .setNegativeButton(android.R.string.no, null).show();
        } else {
            latitude = String.valueOf(location.getLatitude());
            longitude = String.valueOf(location.getLongitude());
            getWeather();
        }
        getActivity().setTitle("Dictionary");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        textNamaKota = (TextView) v.findViewById(R.id.text_nama_kota);
        textSuhu = (TextView) v.findViewById(R.id.text_suhu);
        weatherPanel = (CardView) v.findViewById(R.id.weather_panel);
        loadingPanel = (CardView) v.findViewById(R.id.loading_panel);
        mainWeather = (ImageView) v.findViewById(R.id.image_main);
        linearLeaves = (LinearLayout) v.findViewById(R.id.linear_leaves);
        linearLog = (LinearLayout) v.findViewById(R.id.linear_log);
        linearWater = (LinearLayout) v.findViewById(R.id.linear_water);
        linearCuaca = (LinearLayout) v.findViewById(R.id.linear_cuaca);
        textLoading = (TextView) v.findViewById(R.id.text_loading);
        progressBar = (ProgressBar) v.findViewById(R.id.progress);


        linearLeaves.setOnClickListener(this);
        linearLog.setOnClickListener(this);
        linearWater.setOnClickListener(this);
        linearCuaca.setOnClickListener(this);

        return v;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }


    @Override
    public void onResume() {
        super.onResume();
        location.beginUpdates();
    }

    @Override
    public void onPause() {
        location.endUpdates();
        super.onPause();
    }

    private void getWeather() {
        String units = "metric";
        String appid = "ece1a8cdc266e5c942e10bc8c21a3f50";

        Call<WeatherApi> call = userClient.getWeather(latitude, longitude, units, appid);

        call.enqueue(new Callback<WeatherApi>() {
            @Override
            public void onResponse(Call<WeatherApi> call, Response<WeatherApi> response) {
                if (response.isSuccessful()) {
                    kota = response.body().getName();
                    suhu = response.body().getMain().getTemp().intValue();
                    main = response.body().getWeather().get(0).getMain();
                    setWeatherPanel();
                }
            }

            @Override
            public void onFailure(Call<WeatherApi> call, Throwable t) {
                weatherPanel.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                textLoading.setText("Cannot retrieve weather data");
            }
        });
    }

    private void setWeatherPanel() {
        textNamaKota.setText(kota);
        textSuhu.setText(suhu + " Celcius");
        switch (main.toLowerCase()) {
            case "clear sky":
                mainWeather.setImageDrawable(getResources().getDrawable(R.drawable.ic_clear_sky));
                break;
            case "clouds":
                mainWeather.setImageDrawable(getResources().getDrawable(R.drawable.ic_few_cloud));
                break;
            case "scattered clouds":
            case "broken clouds":
                mainWeather.setImageDrawable(getResources().getDrawable(R.drawable.ic_broken_cloud));
                break;
            case "shower rain":
                mainWeather.setImageDrawable(getResources().getDrawable(R.drawable.ic_shower_rain));
                break;
            case "rain":
                mainWeather.setImageDrawable(getResources().getDrawable(R.drawable.ic_rain));
                break;
            case "thunderstorm":
                mainWeather.setImageDrawable(getResources().getDrawable(R.drawable.ic_thunderstrom));
                break;
            case "snow":
                mainWeather.setImageDrawable(getResources().getDrawable(R.drawable.ic_snow));
                break;
            case "mist":
                mainWeather.setImageDrawable(getResources().getDrawable(R.drawable.ic_mist));
                break;
        }
        weatherPanel.setVisibility(View.VISIBLE);
        loadingPanel.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getActivity(), HamaByDitemukanActivity.class);
        switch (view.getId()) {
            case R.id.linear_leaves:
                intent.putExtra("ditemukan", "leaves");
                startActivity(intent);
                break;
            case R.id.linear_log:
                intent.putExtra("ditemukan", "log");
                startActivity(intent);
                break;
            case R.id.linear_water:
                intent.putExtra("ditemukan", "water");
                startActivity(intent);
                break;
            case R.id.linear_cuaca:
                Intent cuacaIntent = new Intent(getActivity(), CuacaActivity.class);
                cuacaIntent.putExtra("long", longitude);
                cuacaIntent.putExtra("lat", latitude);
                startActivity(cuacaIntent);
                break;
        }
    }
}
