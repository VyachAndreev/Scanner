package com.andreev.scanner.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andreev.scanner.R;
import com.andreev.scanner.adapter.ItemViewHolder;
import com.andreev.scanner.adapter.SearchAdapter;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchFragment extends Fragment {

    private List<String> data = new ArrayList<>();
    private List<String> asd = new ArrayList<>();

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

        EditText searchET = view.findViewById(R.id.search_et);
        ImageButton fSearchButton = view.findViewById(R.id.search_btn);

        RecyclerView recyclerView = view.findViewById(R.id.recycler);

        fSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search = searchET.getText().toString();
                    run("http://ferro-trade.ru/api/search/api/positions");
                recyclerView.setAdapter(new SearchAdapter(data, new ItemClickHandler()));
                recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
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
            final int item = position + 1;

            if (listener != null) {
                listener.onItemClicked(item);
            }
        }
    }

    public void run(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        Log.i("response", url);
        call.enqueue(new Callback() {
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("response", "success");
                Log.i("response", response.toString());
            }

            public void onFailure(Call call, IOException e) {
                Log.i("response", "fail");
            }
        });
    }
}
