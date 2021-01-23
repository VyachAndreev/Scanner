package com.andreev.scanner.interfaces;

import com.andreev.scanner.classes.GetPositionView;
import com.andreev.scanner.fragments.ShipmentFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API {
    @GET("/api/positions")
    Call<List<GetPositionView>> positions();

    @GET("/api/search/{text}")
    Call<List<GetPositionView>> searched(@Path("text") String text);

    @GET("/api/position/{id}")
    Call<GetPositionView> positionById(@Path("id") String id);

    @GET("/api/position/{id}")
    Call<GetPositionView> packageById(@Path("id") String id);

    @POST("/api/search/tag/{text}")
    Call<List<String>> tags(@Path("text") String text);

    @POST("/api/departure?")
    Call<List<String>> shipment(@Query("request") String request, @Query("except") String except);

    @POST("/api/departureConfirmation")
    Call<ShipmentFragment.fileHolder> confirm();
}
