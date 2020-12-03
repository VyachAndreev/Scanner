package com.andreev.scanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.andreev.scanner.fragments.MainFragment;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String[] res = new String[1];
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, new MainFragment())
                .commitAllowingStateLoss();
    }
}