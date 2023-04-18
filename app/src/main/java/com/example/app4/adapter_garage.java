package com.example.app4;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class adapter_garage extends RecyclerView.Adapter<adapter_garage.myviewholder> {

    Context context;
    ArrayList<com.example.app4.data.garage> garage;
    listener_garage ocl;

    public adapter_garage(Context context, ArrayList<com.example.app4.data.garage> garage, listener_garage ocl) {
        this.context = context;
        this.garage = garage;
        this.ocl = ocl;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.get_mechanic, parent, false);
        return new myviewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull adapter_garage.myviewholder holder, int position) {
        holder.id.setText(garage.get(position).getId());
        if (garage.get(position).getName()==null){
            holder.name.setText("");
        } else {
            holder.name.setText(garage.get(position).getName());
        }
        if (garage.get(position).getDistance()==0){
            holder.distance.setText("");
        } else {
            holder.distance.setText(String.valueOf(garage.get(position).getDistance()));
        }
        if (garage.get(position).getDunit()==null){
            holder.dunit.setText("");
        } else {
            holder.dunit.setText(garage.get(position).getDunit());
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dc = String.valueOf(holder.id.getText());
                ocl.onItemClicked(dc, garage.get(position),position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return garage.size();
    }

    public static class myviewholder extends RecyclerView.ViewHolder{
        TextView id, name, distance, dunit;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id);
            name = itemView.findViewById(R.id.name);
            distance = itemView.findViewById(R.id.distance);
            dunit = itemView.findViewById(R.id.dunit);
        }
    }
}
