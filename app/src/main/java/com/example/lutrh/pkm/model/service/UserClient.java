package com.example.lutrh.pkm.model.service;

import com.example.lutrh.pkm.model.Hama;
import com.example.lutrh.pkm.model.ResponseApi;
import com.example.lutrh.pkm.model.WeatherApi;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by lutrh on 10/20/17.
 */

public interface UserClient {

    @Multipart
    @POST("api/")
    Call<ResponseApi> uploadPhoto(
            @Part("k") RequestBody k,
            @Part MultipartBody.Part image
    );

    @POST("weather")
    Call<WeatherApi> getWeather(@Query("lat") String latitude, @Query("lon") String longitude, @Query("units") String units, @Query("APPID") String appid);

    @GET("dictionary/")
    Call<List<Hama>> getDatabase();

}
