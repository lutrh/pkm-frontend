package com.example.lutrh.pkm.model.service;

import com.example.lutrh.pkm.model.ResponseApi;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

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

}
