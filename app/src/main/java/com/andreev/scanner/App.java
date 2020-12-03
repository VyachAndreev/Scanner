package com.andreev.scanner;

import android.app.Application;

import com.andreev.scanner.interfaces.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {
    private static API api;
    private static final String URL = "http://ferro-trade.ru/";

    @Override
    public void onCreate() {
        super.onCreate();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(API.class);
    }

    public static API getApi() {
        return api;
    }
}
