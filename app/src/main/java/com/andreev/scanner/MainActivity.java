package com.andreev.scanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.andreev.scanner.classes.GetPositionView;
import com.andreev.scanner.fragments.ItemFragment;
import com.andreev.scanner.fragments.MainFragment;
import com.andreev.scanner.fragments.SearchFragment;
import com.andreev.scanner.fragments.SeeAllFragment;

public class MainActivity extends AppCompatActivity implements SearchFragment.IListener, SeeAllFragment.IListener {

    public static final String idTAG = "id";
    public static final String TAG = "isPosition";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new MainFragment())
                .commitAllowingStateLoss();
    }

    @Override
    public void onItemClicked(String id, boolean isPosition) {
        Log.i("MainActivity", id);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, ItemFragment.newInstance(id, isPosition))
                .addToBackStack(null)
                .commitAllowingStateLoss();
        Log.i("MainActivity", id);
    }
}
