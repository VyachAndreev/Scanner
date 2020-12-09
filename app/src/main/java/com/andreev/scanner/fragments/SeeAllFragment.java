package com.andreev.scanner.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class SeeAllFragment extends Fragment {

    private final List<GetPositionView> positions = new ArrayList<>();

    public interface IListener {
        public void onItemClicked(GetPositionView item);
    }
    protected IListener mListener;
    private RecyclerView recycler;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        Log.i("SeeAll", "listener ready to attach");
        if (requireActivity() instanceof IListener) {
            mListener = (IListener) requireActivity();
            Log.i("SeeAll", "listenerAttached");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_see_all, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recycler = view.findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(requireContext()));

        try{
            App.getApi().positions().enqueue(new Callback<List<GetPositionView>>() {
                @Override
                public void onResponse(@NotNull Call<List<GetPositionView>> call, @NotNull Response<List<GetPositionView>> response) {
                    positions.clear();
                    if (response.body() != null) {
                        Log.i("response", response.body().toString());
                        positions.addAll(response.body());
                    }
                    if (getActivity() != null)
                        getActivity().runOnUiThread(()->updateAdapter());
                }

                @Override
                public void onFailure(@NotNull Call<List<GetPositionView>> call, @NotNull Throwable t) {
                    Log.e("response", t.getMessage());
                }
            });
        } catch (Exception e){
            Log.e("exceprion occured", e.getMessage());
        }
    }

    private void updateAdapter(){
        SearchAdapter searchAdapter = new SearchAdapter(positions, new ItemClickedHandler());
        recycler.setAdapter(searchAdapter);
    }

    class ItemClickedHandler implements ItemViewHolder.IListener {
        @Override
        public void onItemClicked(int position) {
            final GetPositionView item = positions.get(position);
            if (mListener != null) {
                Log.i("SeeAll", item.getId().toString());
                mListener.onItemClicked(item);
            }
        }
    }
}
