package com.example.lutrh.pkm.layout;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lutrh.pkm.R;
import com.example.lutrh.pkm.helper.DatabaseHelper;
import com.example.lutrh.pkm.model.Hama;
import com.example.lutrh.pkm.model.History;
import com.example.lutrh.pkm.model.ResponseApi;
import com.example.lutrh.pkm.model.service.UserClient;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.lutrh.pkm.layout.HomeActivity.CAMERA_REQUEST;

public class CameraActivity extends AppCompatActivity {

    private File imageFile;
    private LinearLayout viewCalculating;
    private ScrollView scrollDetailHama;
    private DatabaseHelper db;

    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl("https://pkm-server.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create());

    Retrofit retrofit = builder.build();

    UserClient userClient = retrofit.create(UserClient.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initCamera();
        setContentView(R.layout.activity_camera);
        setTitle("Pest Detail");

        db = new DatabaseHelper(this);
        viewCalculating = (LinearLayout) findViewById(R.id.view_calculate);
        scrollDetailHama = (ScrollView) findViewById(R.id.scroll_detail_hama);

        getSupportActionBar().hide();
    }

    private void initCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/PKM");
        boolean isPresent = true;
        if (!pictureDirectory.exists()) {
            isPresent = pictureDirectory.mkdir();
        }
        if (isPresent) {
            String pictureName = getPictureName();
            imageFile = new File(pictureDirectory, pictureName);
            Uri pictureUri = Uri.fromFile(imageFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);
            startActivityForResult(intent, CAMERA_REQUEST);
        } else {
            Toast.makeText(CameraActivity.this, "Error happened", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_REQUEST) {
                RequestBody k = RequestBody.create(MediaType.parse("text/plain"), "1");
                // create RequestBody instance from file
                Uri uri = Uri.fromFile(imageFile);
                RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), imageFile);

                MultipartBody.Part image = MultipartBody.Part.createFormData("image", imageFile.getName(), requestFile);

                Call<ResponseApi> call = userClient.uploadPhoto(k, image);

                call.enqueue(new Callback<ResponseApi>() {
                    @Override
                    public void onResponse(Call<ResponseApi> call, Response<ResponseApi> response) {
                        if (response.isSuccessful()) {
                            viewDetail(response.body());
                        } else {
                            Toast.makeText(CameraActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseApi> call, Throwable t) {
                        Toast.makeText(CameraActivity.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        } else {
            finish();
        }
    }

    private String getPictureName() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = sdf.format(new Date());
        return "PKM-" + timestamp + ".jpg";
    }

    private void viewDetail(ResponseApi responseApi) {
        getSupportActionBar().show();
        ImageView imageHama = (ImageView) findViewById(R.id.image_hama);
        ImageView imageDitemukan = (ImageView) findViewById(R.id.image_ditemukan);
        TextView textHama = (TextView) findViewById(R.id.text_nama_hama);
        TextView textNamaLatin = (TextView) findViewById(R.id.text_nama_latin);
        TextView textDitemukan = (TextView) findViewById(R.id.text_ditemukan);
        TextView textDeskripsi = (TextView) findViewById(R.id.text_deskripsi);
        TextView textSolusi = (TextView) findViewById(R.id.text_solution);

        History history = new History(0, responseApi.getHama());
        db.addHistory(history);
        Hama hama = db.getHama(responseApi.getHama());

        textHama.setText(hama.getNama());
        textNamaLatin.setText(hama.getNamaLatin());
        textDitemukan.setText("Usually found at rice " + hama.getDitemukan());
        textDeskripsi.setText(hama.getDeskripsi());
        textSolusi.setText(hama.getSolusi());

        switch (hama.getNama()) {
            case "wereng":
                imageHama.setImageResource(R.drawable.ig_wereng);
                break;
            case "belalang":
                imageHama.setImageResource(R.drawable.ig_belalang);
                break;
            case "tikus sawah":
                imageHama.setImageResource(R.drawable.ig_tikus);
                break;
            case "walang sangit":
                imageHama.setImageResource(R.drawable.ig_walang_sangit);
                break;
        }

        switch (hama.getDitemukan()) {
            case "leaves":
                imageDitemukan.setImageResource(R.drawable.ic_leafs);
                break;
            case "log":
                imageDitemukan.setImageResource(R.drawable.ic_log);
                break;
            case "water":
                imageDitemukan.setImageResource(R.drawable.ic_water);
                break;
        }
        viewCalculating.setVisibility(View.GONE);
        scrollDetailHama.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}


