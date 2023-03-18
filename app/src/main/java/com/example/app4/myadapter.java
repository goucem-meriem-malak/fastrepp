package com.example.app4;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app4.mechanic_services.mechanics_list;
import com.example.app4.others.home;

import java.util.ArrayList;
import java.util.List;

public class myadapter extends RecyclerView.Adapter<myadapter.myviewholder> {

    Context context;
    ArrayList<item> items;
    selectlistener ocl;

    public myadapter(Context context, ArrayList<item> items, selectlistener ocl) {
        this.context = context;
        this.items = items;
        this.ocl = ocl;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.itemview, parent, false);
        return new myviewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myadapter.myviewholder holder, int position) {
        holder.id.setText(items.get(position).getId());
        holder.name.setText(items.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ocl.onItemClicked(items.get(position));
            }
        });
    }



    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class myviewholder extends RecyclerView.ViewHolder{
        TextView id, name, distance;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id);
            name = itemView.findViewById(R.id.name);
        }
    }
}
