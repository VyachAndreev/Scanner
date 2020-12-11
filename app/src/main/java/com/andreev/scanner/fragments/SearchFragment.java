package com.andreev.scanner.fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

import com.andreev.scanner.App;
import com.andreev.scanner.R;
import com.andreev.scanner.adapters.ItemViewHolder;
import com.andreev.scanner.adapters.SearchAdapter;
import com.andreev.scanner.classes.GetPositionView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {

    private final List<String> hints = new ArrayList<>();
    private final List<GetPositionView> data = new ArrayList<>();
    private boolean wasButtonPressed = false;
    private String textSearched = "";

    private RecyclerView recyclerView;
    private AutoCompleteTextView searchET;
    private TextView nothingFoundTV;

    private static final String KEY_STRING = "textSearched";
    private static final String KEY_BOOLEAN = "wasButtonPressed";

    public interface IListener {
        public void onItemClicked(String id, boolean isPosition);
    }

    protected IListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (requireActivity() instanceof IListener) {
            mListener = (IListener) requireActivity();
        }
    }

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

        if (savedInstanceState != null) {
            textSearched = savedInstanceState.getString(KEY_STRING);
            wasButtonPressed = savedInstanceState.getBoolean(KEY_BOOLEAN);
            if (wasButtonPressed) {
                makeRequestAndSet(textSearched, false);
            }
        }


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
                Log.i("textChangedListener", searchET.getText().toString());
                if (!searchET.getText().toString().isEmpty()) {
                    try {
                        makeRequestAndSet(searchET.getText().toString(), true);
                    } catch (NullPointerException e) {
                        Log.e("exception occured", e.getMessage());
                    }
                }
            }
        });

        fSearchButton.setOnClickListener(view1 -> {
            textSearched = searchET.getText().toString();
            wasButtonPressed = true;
            if (textSearched.isEmpty()) {
                data.clear();
                nothingFoundTV.setVisibility(View.VISIBLE);
            } else {
                try {
                    makeRequestAndSet(textSearched, false);
                } catch (NullPointerException e) {
                    nothingFoundTV.setVisibility(View.VISIBLE);
                    Log.e("exception occured", e.getMessage());
                }
            }
        });
    }


    public void makeRequestAndSet(String text, boolean f) {
        if (f){
            hints.clear();
            try{
                App.getApi().tags(text).enqueue(new Callback<List<String>>() {
                    @Override
                    public void onResponse(@NotNull Call<List<String>> call, @NotNull Response<List<String>> response) {
                        if (response.body() != null) {
                            hints.addAll(response.body());
                            Log.i("response", response.body().toString());
                            if (getActivity() != null) {
                                getActivity().runOnUiThread(() -> setHints());
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<List<String>> call, @NotNull Throwable t) {
                        Log.e("response", call.request().toString());
                        Log.e("response", t.getMessage());
                    }
                });
            } catch (Exception e) {
                Log.e("exception occurred", e.getMessage());
            }
        } else {
            data.clear();
            try{
                App.getApi().searchedPositions(text).enqueue(new Callback<List<GetPositionView>>() {
                    @Override
                    public void onResponse(@NotNull Call<List<GetPositionView>> call, @NotNull Response<List<GetPositionView>> response) {
                        if (response.body() != null) {
                            data.addAll(response.body());
                            Log.i("response", response.body().toString());
                            if (getActivity() != null)
                                getActivity().runOnUiThread(() -> setNewAdapter());
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<List<GetPositionView>> call, @NotNull Throwable t) {
                        Log.e("response", t.getMessage());
                    }
                });
            } catch (Exception e) {
                Log.e("exception occurred", e.getMessage());
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_STRING, textSearched);
        outState.putBoolean(KEY_BOOLEAN, wasButtonPressed);
    }

    void setHints() throws NullPointerException{
        searchET.setAdapter(new ArrayAdapter<>(getContext(),
                android.R.layout.simple_dropdown_item_1line, hints));
        if (hints.size() > 0) {
            Log.i("onTextChangedListener", hints.get(0));
        }
        if (!(hints.size() == 1 && searchET.getText().toString().equals(hints.get(0)))) {
            searchET.showDropDown();
        }

    }

    void setNewAdapter() throws NullPointerException{
        if (data.size() == 0) {
            nothingFoundTV.setVisibility(View.VISIBLE);
        } else {
            nothingFoundTV.setVisibility(View.GONE);
        }
        recyclerView.setAdapter(new SearchAdapter(data, new ItemClickedHandler()));
    }

    class ItemClickedHandler implements ItemViewHolder.IListener {
        @Override
        public void onItemClicked(int position) {
            final GetPositionView item = data.get(position);

            if (mListener != null) {
                mListener.onItemClicked(item.getId().toString(), item.getType().equals("POSITION"));
                Log.i("id", item.getId().toString());
            }
        }
    }
}
