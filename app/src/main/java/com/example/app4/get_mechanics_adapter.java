package com.example.app4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app4.data.get_mechanics;

import java.util.ArrayList;

public class get_mechanics_adapter extends RecyclerView.Adapter<get_mechanics_adapter.myviewholder> {

    Context context;
    ArrayList<com.example.app4.data.get_mechanics> get_mechanics;
    selectlistener ocl;

    public get_mechanics_adapter(Context context, ArrayList<get_mechanics> get_mechanics, selectlistener ocl) {
        this.context = context;
        this.get_mechanics = get_mechanics;
        this.ocl = ocl;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.get_mechanic, parent, false);
        return new myviewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull get_mechanics_adapter.myviewholder holder, int position) {
        holder.id.setText(get_mechanics.get(position).getId());
        holder.name.setText(get_mechanics.get(position).getName());
        holder.distance.setText(String.valueOf(get_mechanics.get(position).getDistance()));
        holder.dunit.setText(get_mechanics.get(position).getDunit());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ocl.onItemClicked(get_mechanics.get(position),position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return get_mechanics.size();
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
