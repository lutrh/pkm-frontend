
package com.example.lutrh.pkm.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Hama {

    @SerializedName("deskripsi")
    @Expose
    private String deskripsi;
    @SerializedName("nama_latin")
    @Expose
    private String namaLatin;
    @SerializedName("ditemukan")
    @Expose
    private String ditemukan;
    @SerializedName("suhu_hidup")
    @Expose
    private String suhuHidup;
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("solusi")
    @Expose
    private String solusi;

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getNamaLatin() {
        return namaLatin;
    }

    public void setNamaLatin(String namaLatin) {
        this.namaLatin = namaLatin;
    }

    public String getDitemukan() {
        return ditemukan;
    }

    public void setDitemukan(String ditemukan) {
        this.ditemukan = ditemukan;
    }

    public String getSuhuHidup() {
        return suhuHidup;
    }

    public void setSuhuHidup(String suhuHidup) {
        this.suhuHidup = suhuHidup;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getSolusi() {
        return solusi;
    }

    public void setSolusi(String solusi) {
        this.solusi = solusi;
    }

}
