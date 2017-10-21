package com.example.lutrh.pkm.model;

/**
 * Created by lutrh on 10/22/17.
 */

public class History {

    private int id;
    private String hama;

    public History(int id, String hama) {
        this.id = id;
        this.hama = hama;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHama() {
        return hama;
    }

    public void setHama(String hama) {
        this.hama = hama;
    }
}
