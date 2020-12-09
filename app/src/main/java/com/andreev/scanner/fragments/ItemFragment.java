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

import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemFragment extends Fragment {

    private GetPositionView item;
    private TextView mark;
    private TextView diameter;
    private TextView packing;
    private TextView party;
    private TextView melting;
    private TextView mass;
    private TextView status;
    private TextView manufacturer;

    public static ItemFragment newInstance(GetPositionView item) {

        final Bundle extras = new Bundle();
        extras.putSerializable(MainActivity.TAG, item);
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

        item = item();

        if (item != null) {

            mark = view.findViewById(R.id.mark);
            diameter = view.findViewById(R.id.diameter);
            packing = view.findViewById(R.id.packing);
            party = view.findViewById(R.id.party);
            melting = view.findViewById(R.id.melting);
            mass = view.findViewById(R.id.mass);
            status = view.findViewById(R.id.status);
            manufacturer = view.findViewById(R.id.made_by);

            mark.setText(item.getMark());
            diameter.setText(item.getDiameter());
            packing.setText(item.getPacking());
            party.setText(item.getPart());
            melting.setText(item.getPlav());
            mass.setText(item.getMass().toString());
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
            status.setText("Статус: " + a);
        }
    }

    public GetPositionView item() {
        if (getArguments() == null) {
            return null;
        }

        final Serializable item =  getArguments().getSerializable(MainActivity.TAG);
        if (item instanceof GetPositionView) {
            return (GetPositionView) getArguments().getSerializable(MainActivity.TAG);
        }
        return null;
    }
}
