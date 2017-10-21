package com.example.lutrh.pkm.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lutrh.pkm.R;
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

public class HomeFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private SimpleLocation location;
    private TextView textNamaKota, textSuhu;
    private int suhu;
    private String kota, main;
    private CardView weatherPanel, loadingPanel;
    private ImageView mainWeather;

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
                    .setMessage("RPD need access to your location")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            SimpleLocation.openSettings(getActivity());
                        }
                    })
                    .setNegativeButton(android.R.string.no, null).show();
        }
        if (kota == null || suhu == 0) {
            getWeather();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        textNamaKota = (TextView) v.findViewById(R.id.text_nama_kota);
        textSuhu = (TextView) v.findViewById(R.id.text_suhu);
        weatherPanel = (CardView) v.findViewById(R.id.weather_panel);
        loadingPanel = (CardView) v.findViewById(R.id.loading_panel);
        mainWeather = (ImageView) v.findViewById(R.id.image_main);
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
        String latitude = String.valueOf(location.getLatitude());
        String longitude = String.valueOf(location.getLongitude());
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
                loadingPanel.setVisibility(View.GONE);
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
}
