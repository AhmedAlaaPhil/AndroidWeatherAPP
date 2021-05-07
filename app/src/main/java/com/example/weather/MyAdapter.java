package com.example.weather;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    String[] cities ;
    Context context;
    public MyAdapter (Context cn , String [] city){
       context = cn;
       cities = city;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
       View view = layoutInflater.inflate(R.layout.myrow,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
             holder.textView.setText(cities[position]);
             holder.mainLayout.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     Intent intent = new Intent(context, MainActivity.class);
                     intent.putExtra("city",cities[position]);
                     context.startActivity(intent);
                 }
             });
    }

    @Override
    public int getItemCount() {
        return cities.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        ConstraintLayout mainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            mainLayout = itemView.findViewById(R.id.main);
        }
    }
}
