package com.example.weather;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Example {

    @SerializedName("weather")
   List <Weather> weather;

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    @SerializedName("main")
    Main main ;


    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }
}
