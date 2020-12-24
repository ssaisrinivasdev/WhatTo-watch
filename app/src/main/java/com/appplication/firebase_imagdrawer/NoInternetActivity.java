package com.appplication.firebase_imagdrawer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class NoInternetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.no_internet_activity);
        Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
    }
}