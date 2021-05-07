package com.example.weather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.logging.SimpleFormatter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private static final String sharedPrfName = "MySharedPref";
    private static final String sharedPrfPre = "MyPress";
    private static final String sharedPrfHum = "MyHum";
    private static final String sharedPrfTemp = "MyTemp";
    private static final String sharedPrfState = "MyState";
    private static final String sharedPrfTime = "MyTime";
    LocationManager locationManager;
    String city = "Cairo";
    Double temp = 0.0;
    int defulatP;
    int defulatH;
    String state = "Non";
    String myTime = "Never";
    double longt;
    double lat;
    private String provider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getData();

        TextView textView = (TextView) findViewById(R.id.country);
        textView.setText(city);
        getSavedData();
        TextView textView3 = (TextView) findViewById(R.id.textView3);
        TextView textView2 = (TextView) findViewById(R.id.textView2);
        TextView textView4 = (TextView) findViewById(R.id.textView5);
        TextView textView5 = (TextView) findViewById(R.id.textView4);
        textView5.setText(myTime);
        textView3.setText("" + defulatH + "%");
        textView2.setText("" + defulatP + "PH");
        textView4.setText(state);
        displayWeather(temp);


    }


    public void getWeather(View view) {

       if (city.equals("Current Location")) {

            apilocation();

       } else {
            Displaypandh();

        }
        displayWeather(temp);

    }

    private void getData() {
        if (getIntent().hasExtra("city")) {
            city = getIntent().getStringExtra("city");

        }

    }


    private void displayWeather(Double temp) {

        TextView textView = (TextView) findViewById(R.id.tempView);
        textView.setText(String.format("%.2f", temp) + "F");
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        if (temp > 20) {

            imageView.setBackgroundResource(R.drawable.sunny);

        } else if (temp == 20 || temp > 10) {

            imageView.setBackgroundResource(R.drawable.spring);

        } else {

            imageView.setBackgroundResource(R.drawable.winter);

        }

    }

    private void Displaypandh() {

        Retrofit retrofit = new Retrofit.Builder().
                baseUrl("https://api.openweathermap.org/data/2.5/").
                addConverterFactory(GsonConverterFactory.create()).
                build();
        GoogleWeatherAPI googleWeatherAPI = retrofit.create(GoogleWeatherAPI.class);
        Call<Example> call = googleWeatherAPI.getPost(city + ",eg,eg", "5591cb3fcc2d6c4445455fd59b39b5be", "imperial");

        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                if (!response.isSuccessful()) {

                    Toast.makeText(MainActivity.this, "City Error", Toast.LENGTH_LONG).show();


                }

                Example example = response.body();
                Main main = example.getMain();
                List<Weather> weather = example.getWeather();
                TextView textView = (TextView) findViewById(R.id.textView3);
                TextView textView2 = (TextView) findViewById(R.id.textView2);
                TextView textView3 = (TextView) findViewById(R.id.textView5);
                int hum = main.getHumidity();
                int press = main.getPressure();
                state = weather.get(0).getMain();
                textView.setText("" + hum + "%");
                textView2.setText("" + press + "PH");
                textView3.setText(state);
                temp = main.getTemp();
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat simpleFormatter = new SimpleDateFormat("hh:mm:ss");
                myTime = simpleFormatter.format(calendar.getTime());
                TextView textView5 = (TextView) findViewById(R.id.textView4);
                textView5.setText(myTime);
                setSavedData(hum, press, temp, state, myTime);
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }


    private void apilocation() {

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = LocationManager.GPS_PROVIDER;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED){
            return;
        }
        Location location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
        onLocationChanged(location);
        Retrofit retrofit = new Retrofit.Builder().
                baseUrl("https://api.openweathermap.org/data/2.5/").
                addConverterFactory(GsonConverterFactory.create()).
                build();
        GoogleWeatherAPI2 googleWeatherAPI = retrofit.create(GoogleWeatherAPI2.class);
        Call<Example> call = googleWeatherAPI.getCurrentPost(location.getLongitude(),location.getLatitude(),"5591cb3fcc2d6c4445455fd59b39b5be","imperial");

        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                if(!response.isSuccessful()){

                    Toast.makeText(MainActivity.this, "City Error" ,Toast.LENGTH_LONG).show();


                }

                Example example =  response.body();
                Main main = example.getMain();
                List<Weather> weather= example.getWeather();
                TextView textView = (TextView) findViewById(R.id.textView3);
                TextView textView2 = (TextView) findViewById(R.id.textView2);
                TextView textView3 = (TextView) findViewById(R.id.textView5);
                int hum = main.getHumidity();
                int press = main.getPressure();
                state = weather.get(0).getMain();
                textView.setText(""+hum+"%");
                textView2.setText(""+press+"PH");
                textView3.setText(state);
                temp = main.getTemp();

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat simpleFormatter = new SimpleDateFormat( "hh:mm:ss") ;
                myTime = simpleFormatter.format(calendar.getTime());
                TextView textView5 = (TextView) findViewById(R.id.textView4);
                textView5.setText(myTime);

                setSavedData(hum,press,temp,state,myTime);
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage() ,Toast.LENGTH_LONG).show();
            }
        });



    }

    public void setSavedData (int hum , int pree , double temp , String s1,String S2){

        SharedPreferences sharedPreferences = getSharedPreferences(sharedPrfName ,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(sharedPrfHum , hum );
        editor.putInt(sharedPrfPre , pree );
        editor.putFloat(sharedPrfTemp , (float) temp);
        editor.putString(sharedPrfState,s1);
        editor.putString(sharedPrfTime, S2);
        editor.apply();


    }
    public void getSavedData (){
        SharedPreferences sharedPreferences = getSharedPreferences(sharedPrfName ,MODE_PRIVATE);
        defulatH = sharedPreferences.getInt(sharedPrfHum,0);
        defulatP = sharedPreferences.getInt(sharedPrfPre , 0);
        temp = (double) sharedPreferences.getFloat(sharedPrfTemp , 0);
        state = sharedPreferences.getString(sharedPrfState,"Non");
        myTime = sharedPreferences.getString(sharedPrfTime , "Never");



    }


    @Override
    public void onLocationChanged(@NonNull Location location) {



    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }
}