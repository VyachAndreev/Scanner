package com.andreev.scanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.andreev.scanner.classes.GetPositionView;
import com.andreev.scanner.fragments.ItemFragment;
import com.andreev.scanner.fragments.MainFragment;
import com.andreev.scanner.fragments.ReceiveFragment;
import com.andreev.scanner.fragments.SearchFragment;
import com.andreev.scanner.fragments.SeeAllFragment;
import com.andreev.scanner.fragments.ShipmentFragment;

public class MainActivity extends AppCompatActivity implements SearchFragment.IListener, SeeAllFragment.IListener {

    public static final String idTAG = "id";
    public static final String TAG = "isPosition";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new MainFragment())
                    .commitAllowingStateLoss();
        }
    }

    @Override
    public void onItemClicked(String id, boolean isPosition) {
        Log.i("MainActivity", id);
        goToItem(id, isPosition);
        Log.i("MainActivity", id);
    }

    public void goToMain() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new MainFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    public void goToSearch() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new SearchFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    public void goToSeeAll() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new SeeAllFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    public void goToItem(String id, boolean isPosition) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, ItemFragment.newInstance(id, isPosition))
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    public void goToShipment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new ShipmentFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    public void goToReceiver() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new ReceiveFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }
}
