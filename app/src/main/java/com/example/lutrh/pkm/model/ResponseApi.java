package com.example.lutrh.pkm.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseApi {

    @SerializedName("hama")
    @Expose
    private String hama;
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("akurasi")
    @Expose
    private String akurasi;

    public String getHama() {
        return hama;
    }

    public void setHama(String hama) {
        this.hama = hama;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getAkurasi() {
        return akurasi;
    }

    public void setAkurasi(String akurasi) {
        this.akurasi = akurasi;
    }

}