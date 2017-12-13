package com.example.lutrh.pkm.layout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.lutrh.pkm.R;
import com.example.lutrh.pkm.fragment.HistoryFragment;
import com.example.lutrh.pkm.fragment.HomeFragment;

public class HomeActivity extends AppCompatActivity {

    public static final int CAMERA_REQUEST = 20;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setTitle("Home");
                    fragmentTransaction.replace(R.id.frame_layout, new HomeFragment(), "HOME").commit();
                    return true;
                case R.id.navigation_dashboard:
                    startActivity(new Intent(HomeActivity.this, CameraActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.navigation_notifications:
                    setTitle("History");
                    fragmentTransaction.replace(R.id.frame_layout, new HistoryFragment(), "HISTORY").commit();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, new HomeFragment(), "HOME").commit();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
}
