package com.example.weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GoogleWeatherAPI {

   @GET("weather")
    Call<Example> getPost(@Query("q") String s1 , @Query("appid") String sid , @Query("units") String uni);


}
