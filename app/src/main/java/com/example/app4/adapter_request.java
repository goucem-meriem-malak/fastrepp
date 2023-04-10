package com.example.app4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app4.data.get_requests;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class adapter_request extends RecyclerView.Adapter<adapter_request.myviewholder> {
    Context context;
    ArrayList<com.example.app4.data.get_requests> get_requests;
    private double distance;

    public adapter_request(Context context, ArrayList<get_requests> get_requests) {
        this.context = context;
        this.get_requests = get_requests;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.get_requests, parent, false);
        return new myviewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull adapter_request.myviewholder holder, int position) {
        com.example.app4.data.get_requests request = get_requests.get(position);
        if(request.getAddress()==null){
            holder.address.setText("");
        } else {
            holder.address.setText(request.getAddress().toString());
        }
        if(request.getDate()==null){
            holder.date.setText("");
        } else {
            holder.date.setText(request.getDate().toDate().toString());
        }
        if(request.getType()==null){
            holder.type.setText("");
        } else {
            holder.type.setText(request.getType());
        }
        if (holder.type.getText().equals("mechanic")){
            holder.price.setVisibility(View.INVISIBLE);
        }else{
            /* if(request.getPrice()==null){
                holder.price.setText("");
            } else {
                holder.price.setText(String.valueOf(request.getPrice()));
            }*/
        }
        if(request.getState()==null){
            holder.state.setText("");
        } else {
            holder.state.setText(request.getState());
        }
    }

    @Override
    public int getItemCount() {
        return get_requests.size();
    }

    public static class myviewholder extends RecyclerView.ViewHolder{
        TextView id, client_id, mechanic_id, address, mechanic_location, date, type, state, price, distance;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            address = itemView.findViewById(R.id.address);
            date = itemView.findViewById(R.id.date);
            state = itemView.findViewById(R.id.state);
            type = itemView.findViewById(R.id.type);
            price = itemView.findViewById(R.id.price);
        }
    }
}
