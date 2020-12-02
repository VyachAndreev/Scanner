package com.andreev.scanner.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andreev.scanner.R;
import com.andreev.scanner.adapters.SearchAdapter;
import com.andreev.scanner.classes.GetPositionView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchFragment extends Fragment {

    private List<String> hints = new ArrayList<>();
    private List<GetPositionView> data = new ArrayList<>();
    private RecyclerView recyclerView;
    private AutoCompleteTextView searchET;
    private TextView nothingFoundTV;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nothingFoundTV = view.findViewById(R.id.nothing_found);
        nothingFoundTV.setVisibility(View.GONE);
        searchET = view.findViewById(R.id.search_et);
        ImageButton fSearchButton = view.findViewById(R.id.search_btn);

        recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));


        searchET.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                fSearchButton.callOnClick();
            }
            return false;
        });

        searchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.i("textChangedListener", "http://ferro-trade.ru/api/search/tag/" + searchET.getText().toString());
                if (!searchET.getText().toString().isEmpty()) {
                    try {
                        makeRequestAndSet("http://ferro-trade.ru/api/search/tag/" + searchET.getText().toString(), true);
                    } catch (Exception e) {
                        Log.e("exception occured", e.getMessage());
                    }
                }
            }
        });

        fSearchButton.setOnClickListener(view1 -> {
            String search = searchET.getText().toString();
            try {
                makeRequestAndSet("http://ferro-trade.ru/api/search/" + search, false);
            } catch(Exception e){
                nothingFoundTV.setVisibility(View.VISIBLE);
                Log.e("exception occured", e.getMessage());
            }
        });
    }

    public void makeRequestAndSet(String url, boolean f) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        final String[] res = new String[1];
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(@NotNull Call call, IOException e) {
                Log.i("response", e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                res[0] = response.body().string();
                Log.i("response", res[0]);
                Gson gson = new Gson();
                if (f){
                    hints.clear();
                    try {
                        List<String> list = gson.fromJson(res[0],
                                new TypeToken<List<String>>() {
                                }.getType());
                        hints.addAll(list);
                        getActivity().runOnUiThread(() -> setHints());
                    } catch (Exception e) {
                        Log.e("exception occured", e.getMessage());
                    }
                } else {
                    data.clear();
                    try {
                        List<GetPositionView> list = gson.fromJson(res[0],
                                new TypeToken<List<GetPositionView>>() {
                                }.getType());
                        Log.i("response", String.valueOf(list.size()));
                        data.addAll(list);
                    } catch (Exception e) {
                        Log.e("exception occured", e.getMessage());
                    }
                        getActivity().runOnUiThread(() -> setNewAdapter());
                }
            }

        });
    }

    void setHints(){
        searchET.setAdapter(new ArrayAdapter<>(getContext(),
                android.R.layout.simple_dropdown_item_1line, hints));
        if (hints.size() > 0) {
            Log.i("onTextChangedListener", hints.get(0));
        }
        if (!(hints.size() == 1 && searchET.getText().toString().equals(hints.get(0)))) {
            searchET.showDropDown();
        }

    }

    void setNewAdapter(){
        if (data.size() == 0){
            nothingFoundTV.setVisibility(View.VISIBLE);
        } else {
            nothingFoundTV.setVisibility(View.GONE);
        }
        recyclerView.setAdapter(new SearchAdapter(data));
    }
}
