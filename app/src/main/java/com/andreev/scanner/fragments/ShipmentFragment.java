package com.andreev.scanner.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.andreev.scanner.App;
import com.andreev.scanner.R;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShipmentFragment extends Fragment {

    private boolean isSecondUI;
    private TextView upperTextView;
    private TextView lowerTextView;
    private TextView massTextView;
    private EditText upperEditText;
    private EditText lowerEditText;

    private final static String KEY_IS_SECOND = "isSecond";

    public ShipmentFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shipment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        upperTextView = view.findViewById(R.id.upper_tv);
        lowerTextView = view.findViewById(R.id.lower_tv);
        massTextView = view.findViewById(R.id.mass);
        massTextView.append("0");
        upperEditText = view.findViewById(R.id.upper_etv);
        lowerEditText = view.findViewById(R.id.lower_etv);

        if (savedInstanceState != null) {
            isSecondUI = savedInstanceState.getBoolean(KEY_IS_SECOND);
        }

        if (isSecondUI) {
            setSecondUI();
        } else {
            upperEditText.setOnEditorActionListener((v, actionId, event) -> {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    upperEditText.append(", ");
                }
                return true;
            });
        }

        view.findViewById(R.id.shipment_btn).setOnClickListener(view1 -> {
            if (!isSecondUI) {
                //TODO send request
                setSecondUI();
                isSecondUI = true;
                App.getApi().shipment(upperEditText.getText().toString(), lowerEditText.getText().toString()).enqueue(new Callback<List<String>>() {
                    @Override
                    public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                    }

                    @Override
                    public void onFailure(Call<List<String>> call, Throwable t) {
                    }
                });
            } else {
                //TODO fix the bug
                App.getApi().confirm().enqueue(new Callback<fileHolder>() {
                    @Override
                    public void onResponse(@NotNull Call<fileHolder> call, @NotNull Response<fileHolder> response) {
                        if (response.body() != null) {
                            if (getActivity() != null) {
                                Log.i("FILE", response.body().file);
                                //getActivity().runOnUiThread(() -> openBrowser(response.body().file));
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<fileHolder> call, Throwable t) {
                        Log.i("FILE", "FAILED");
                    }
                });
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_IS_SECOND, isSecondUI);
    }

    private void setSecondUI() {
        upperTextView.setText(R.string.agent);
        lowerTextView.setText(R.string.sum);
        massTextView.setVisibility(View.GONE);
        upperEditText.setText("");
        lowerEditText.setText("");
        upperEditText.setOnEditorActionListener(null);
        lowerEditText.setOnEditorActionListener(null);
    }

    private void openBrowser(String file) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://ferro-trafe/api/files/" + file));

        String title = "Открыть в";

        Intent chooser = Intent.createChooser(intent, title);
        startActivity(chooser);
    }

    public static class fileHolder{
        @SerializedName("id")
        @Expose
        int id;
        @SerializedName("file")
        @Expose
        String file;
    }
}
