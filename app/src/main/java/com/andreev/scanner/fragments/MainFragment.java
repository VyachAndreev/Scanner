package com.andreev.scanner.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.andreev.scanner.MainActivity;
import com.andreev.scanner.R;

public class MainFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        view.findViewById(R.id.see_all_btn).setOnClickListener(view1 -> {
            if (getActivity() != null)
                ((MainActivity) getActivity()).goToSeeAll();
        });

        view.findViewById(R.id.search_btn).setOnClickListener(view12 -> {
            if (getActivity() != null)
                ((MainActivity) getActivity()).goToSearch();
        });

        view.findViewById(R.id.shipment_btn).setOnClickListener(view13 -> {
            if (getActivity() != null)
                ((MainActivity) getActivity()).goToShipment();
        });

        view.findViewById(R.id.receiver_btn).setOnClickListener(view13 -> {
            if (getActivity() != null)
                ((MainActivity) getActivity()).goToReceiver();
        });

    }
}
