package com.example.lutrh.pkm.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lutrh.pkm.R;
import com.example.lutrh.pkm.model.List;
import com.example.lutrh.pkm.model.WeatherDetail;

import java.util.ArrayList;

/**
 * Created by lutrh on 11/12/17.
 */

public class CuacaAdapter extends RecyclerView.Adapter<CuacaAdapter.ViewHolder> {

    private java.util.List<com.example.lutrh.pkm.model.List> mCuaca;
    private WeatherDetail weatherDetail;
    private Context mContext;

    public CuacaAdapter(java.util.List<com.example.lutrh.pkm.model.List> mCuaca, Context mContext, WeatherDetail weatherDetail) {
        this.mCuaca = mCuaca;
        this.mContext = mContext;
        this.weatherDetail = weatherDetail;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_cuaca, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final List cuaca = mCuaca.get(position);
        holder.kota.setText(cuaca.getWeather().get(0).getDescription().substring(0,1).toUpperCase() + cuaca.getWeather().get(0).getDescription().substring(1));
        holder.suhu.setText(cuaca.getMain().getTemp().toString().substring(0,2) + " Celcius");
        holder.tanggal.setText(cuaca.getDtTxt());
        setWeatherPanel(holder, cuaca.getWeather().get(0).getMain());
    }

    private void setWeatherPanel(ViewHolder holder, String cuaca) {
        switch (cuaca.toLowerCase()) {
            case "clear sky":
                holder.image.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_clear_sky));
                break;
            case "clouds":
                holder.image.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_few_cloud));
                break;
            case "scattered clouds":
            case "broken clouds":
                holder.image.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_broken_cloud));
                break;
            case "shower rain":
                holder.image.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_shower_rain));
                break;
            case "rain":
                holder.image.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_rain));
                break;
            case "thunderstorm":
                holder.image.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_thunderstrom));
                break;
            case "snow":
                holder.image.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_snow));
                break;
            case "mist":
                holder.image.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_mist));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mCuaca.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView kota, suhu, tanggal;

        public ViewHolder(View itemView) {
            super(itemView);

            image = (ImageView) itemView.findViewById(R.id.image_main);
            kota = (TextView) itemView.findViewById(R.id.text_kota);
            suhu = (TextView) itemView.findViewById(R.id.text_suhu);
            tanggal = (TextView) itemView.findViewById(R.id.text_tanggal);

        }
    }
}
