package com.example.weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GoogleWeatherAPI2 {
    @GET("weather")
    Call<Example> getCurrentPost(@Query("lon") double longitude, @Query("lat") double latitude, @Query("appid") String apikey,@Query("units") String uni);

}
