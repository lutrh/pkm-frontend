package com.example.lutrh.pkm.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.lutrh.pkm.model.Hama;
import com.example.lutrh.pkm.model.History;

import java.util.LinkedList;
import java.util.List;

import static android.R.id.list;

/**
 * Created by lutrh on 10/22/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String LOG = "DatabaseHelper";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "RPD";

    private static final String CREATE_TABLE_HAMA = "CREATE TABLE hama (nama TEXT PRIMARY_KEY, nama_latin TEXT, deskripsi TEXT, solusi TEXT, ditemukan TEXT, suhu_hidup TEXT)";
    private static final String CREATE_TABLE_HISTORY = "CREATE TABLE history (id INTEGER PRIMARY_KEY, hama TEXT)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_HAMA);
        sqLiteDatabase.execSQL(CREATE_TABLE_HISTORY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS hama");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS history");

        onCreate(sqLiteDatabase);
    }

    public void addHama(Hama hama) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nama", hama.getNama());
        values.put("nama_latin", hama.getNamaLatin());
        values.put("deskripsi", hama.getDeskripsi());
        values.put("solusi", hama.getSolusi());
        values.put("ditemukan", hama.getDitemukan());
        values.put("suhu_hidup", hama.getSuhuHidup());

        db.insert("hama", null, values);
        db.close();
    }

    public Hama getHama(String nama) {
        SQLiteDatabase db = getReadableDatabase();
        String[] table = {"nama", "nama_latin", "deskripsi", "solusi", "ditemukan", "suhu_hidup"};
        Cursor cursor = db.query("hama", table, " nama = ? ", new String[]{nama}, null, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            Hama hama = new Hama();
            hama.setNama(cursor.getString(0));
            hama.setNamaLatin(cursor.getString(1));
            hama.setDeskripsi(cursor.getString(2));
            hama.setSolusi(cursor.getString(3));
            hama.setDitemukan(cursor.getString(4));
            hama.setSuhuHidup(cursor.getString(5));
            return hama;
        }
        return null;
    }

    public void addHistory(History history) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("hama", history.getHama());

        db.insert("history", null, values);
        db.close();
    }

    public List<History> getAllHistory() {
        List<History> listHistory = new LinkedList<>();

        String query = "SELECT * FROM history ORDER BY id DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        History history = null;

        if (cursor.moveToFirst()) {
            do {
                history = new History(cursor.getInt(0), cursor.getString(1));
                listHistory.add(history);
            } while (cursor.moveToNext());
        }
        return listHistory;
    }

    public List<Hama> getHamaByDitemukan(String ditemukan) {
        List<Hama> listHama = new LinkedList<>();

        String query = "SELECT * FROM hama where ditemukan = '" + ditemukan + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Hama hama = null;

        if (cursor.moveToFirst()) {
            do {
                hama = new Hama();
                hama.setNama(cursor.getString(0));
                hama.setNamaLatin(cursor.getString(1));
                hama.setDeskripsi(cursor.getString(2));
                hama.setSolusi(cursor.getString(3));
                hama.setDitemukan(cursor.getString(4));
                hama.setSuhuHidup(cursor.getString(5));
                listHama.add(hama);
            } while (cursor.moveToNext());
        }
        return listHama;
    }
}
