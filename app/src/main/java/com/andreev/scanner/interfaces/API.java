package com.andreev.scanner.interfaces;

import com.andreev.scanner.classes.GetPositionView;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface API {
    @GET("/api/positions")
    Call<List<GetPositionView>> positions();

    @GET("/api/search/{text}")
    Call<List<GetPositionView>> searchedPositions(@Path("text") String text);

    @GET("/api/search/tag/{text}")
    Call<List<String>> tags(@Path("text") String text);
}
