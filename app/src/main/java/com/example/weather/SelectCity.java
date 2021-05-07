package com.example.weather;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SelectCity extends AppCompatActivity {
    String cities[];
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectcity);
        recyclerView = findViewById(R.id.RV);
        cities = getResources().getStringArray(R.array.Cities);
        MyAdapter myAdapter = new MyAdapter(this , cities);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
