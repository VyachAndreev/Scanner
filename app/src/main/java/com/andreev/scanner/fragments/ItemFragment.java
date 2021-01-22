package com.andreev.scanner.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.andreev.scanner.App;
import com.andreev.scanner.MainActivity;
import com.andreev.scanner.R;
import com.andreev.scanner.classes.GetPositionView;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemFragment extends Fragment {

    public static ItemFragment newInstance(String id, boolean isPosition) {

        final Bundle extras = new Bundle();
        extras.putString(MainActivity.idTAG, id);
        extras.putBoolean(MainActivity.TAG, isPosition);
        final ItemFragment fragment = new ItemFragment();
        fragment.setArguments(extras);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_item_table, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        final String id = id();
        final Boolean isPosition = isPosition();

        view.findViewById(R.id.button_to_main).setOnClickListener(view1 -> {
            if (getActivity() != null) {
                ((MainActivity) getActivity()).goToMain();
            }
        });
        if (id != null && isPosition != null) {
            if (isPosition) {
                try {
                    App.getApi().positionById(id).enqueue(new Callback<GetPositionView>() {
                        @Override
                        public void onResponse(Call<GetPositionView> call, Response<GetPositionView> response) {
                            if (response.body() != null) {
                                Log.i("ItemResponse", response.body().toString());
                                setUI(view, response.body());
                            }
                        }

                        @Override
                        public void onFailure(Call<GetPositionView> call, Throwable t) {
                            Log.i("ItemResponse", "failed");
                        }
                    });
                } catch (Exception e) {
                    Log.e("exception occurred", e.getMessage());
                }
            }
        } else {
            // TODO package
        }
    }

    public String id() {
        if (getArguments() == null) {
            return null;
        }
        return getArguments().getString(MainActivity.idTAG);
    }

    public Boolean isPosition() {
        if (getArguments() == null) {
            return null;
        }
        return getArguments().getBoolean(MainActivity.TAG);
    }

    private void setUI(View view, GetPositionView item) {
        TextView mark = view.findViewById(R.id.mark);
        TextView diameter = view.findViewById(R.id.diameter);
        TextView packing = view.findViewById(R.id.packing);
        TextView party = view.findViewById(R.id.party);
        TextView melting = view.findViewById(R.id.melting);
        TextView mass = view.findViewById(R.id.mass);
        TextView status = view.findViewById(R.id.status);
        TextView manufacturer = view.findViewById(R.id.made_by);

        mark.setText(item.getMark());
        diameter.setText(item.getDiameter());
        packing.setText(item.getPacking());
        party.setText(item.getPart());
        melting.setText(item.getPlav());
        mass.setText(String.valueOf(item.getMass()));
        manufacturer.setText(item.getManufacturer());
        String a;
        switch (item.getStatus()) {
            case "Arriving":
                a = "Прибывает";
                break;
            case "In_stock":
                a = "На складе";
                break;
            case "Departured":
                a = "Отгружен";
                break;
            default:
                a = "Местоположение неизвестно";
                break;
        }
        status.setText(a);
    }
}
