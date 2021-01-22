package com.andreev.scanner.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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

        Button seeAllButton = view.findViewById(R.id.see_all_btn);
        Button searchButton = view.findViewById(R.id.search_btn);

        seeAllButton.setOnClickListener(view1 -> {
            if (getActivity() != null)
                ((MainActivity) getActivity()).goToSeeAll();
        });

        searchButton.setOnClickListener(view12 -> {
            if (getActivity() != null)
                ((MainActivity) getActivity()).goToSearch();
        });

    }
}
