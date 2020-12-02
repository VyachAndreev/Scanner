package com.andreev.scanner.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andreev.scanner.BuildConfig;
import com.andreev.scanner.R;
import com.andreev.scanner.adapter.ItemViewHolder;
import com.andreev.scanner.adapter.SearchAdapter;
import com.andreev.scanner.classes.GetPositionView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SearchFragment extends Fragment {

    private List<GetPositionView> data = new ArrayList<GetPositionView>();
    private List<String> asd = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private SearchAdapter mSearchAdapter;


    public interface IListener {
        void onItemClicked(int item);
    }

    protected IListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (requireActivity() instanceof IListener) {
            listener = (IListener) requireActivity();
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

        asd.add("sdad");
        asd.add("adasdasd");

        EditText searchET = view.findViewById(R.id.search_et);
        ImageButton fSearchButton = view.findViewById(R.id.search_btn);

        mRecyclerView = view.findViewById(R.id.recycler);
        mSearchAdapter = new SearchAdapter(data, new ItemClickHandler());
        mRecyclerView.setAdapter(mSearchAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        fSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search = searchET.getText().toString();
                if (search.isEmpty()){
                    //TODO warning U SHOULD PRINT SMTH
                } else {
                    makeRequestAndSet("http://ferro-trade.ru/api/search/" + search);
                }
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();

        listener = null;
    }

    class ItemClickHandler implements ItemViewHolder.IListener {
        @Override
        public void onItemClicked(int position) {
            listener.onItemClicked(position);
        }
    }

    public void makeRequestAndSet(String url) {
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
                List<GetPositionView> list = gson.fromJson(res[0],
                        new TypeToken<List<GetPositionView>>() {
                        }.getType());
                Log.i("response", String.valueOf(list.size()));
                for (int i = 0; i < list.size(); i++) {
                    asd.add(list.get(i).toString());
                }
                data.clear();
                data.addAll(list); // тут уже объекты
                getActivity().runOnUiThread(() -> mSearchAdapter.notifyDataSetChanged());
            }

        });
    }
}
