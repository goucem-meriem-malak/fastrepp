package com.example.app4;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app4.data.tow;

import java.util.ArrayList;

public class adapter_tow extends RecyclerView.Adapter<adapter_tow.myviewholder> {

    Context context;
    ArrayList<tow> tows;
    list_tows ocl;

    public adapter_tow(Context context, ArrayList<tow> tows, list_tows ocl) {
        this.context = context;
        this.tows = tows;
        this.ocl = ocl;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.get_tow, parent, false);
        return new myviewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull adapter_tow.myviewholder holder, int position) {
        holder.id.setText(tows.get(position).getId());
        holder.firstname.setText(tows.get(position).getFirstname());
        holder.distance.setText(String.valueOf(tows.get(position).getDistance()));
        holder.dunit.setText(tows.get(position).getDunit());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dc = String.valueOf(holder.id.getText());
                ocl.onItemClicked(dc, tows.get(position),position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tows.size();
    }

    public static class myviewholder extends RecyclerView.ViewHolder{
        TextView id, firstname, lastname, distance, dunit;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id);
            firstname = itemView.findViewById(R.id.firstname);
            lastname = itemView.findViewById(R.id.lastname);
            distance = itemView.findViewById(R.id.distance);
            dunit = itemView.findViewById(R.id.dunit);
        }
    }
}