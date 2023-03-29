package com.example.app4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app4.data.get_requests;

import java.util.ArrayList;

public class adapter_requests_worker extends RecyclerView.Adapter<adapter_requests_worker.myviewholder> {
    Context context;
    ArrayList<com.example.app4.data.get_requests> get_requests;

    public adapter_requests_worker(Context context, ArrayList<get_requests> get_requests) {
        this.context = context;
        this.get_requests = get_requests;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.get_requests_worker, parent, false);
        return new myviewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        com.example.app4.data.get_requests request = get_requests.get(position);
        holder.client_location.setText(request.getClient_location().toString());
        holder.price.setText(String.valueOf(request.getPrice()));
        holder.state.setText(request.getState());
    }

    @Override
    public int getItemCount() {
        return get_requests.size();
    }

    public static class myviewholder extends RecyclerView.ViewHolder{
        TextView id, client_id, mechanic_id, client_location, mechanic_location, type, state, price, distance;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            client_location = itemView.findViewById(R.id.client_location);
            state = itemView.findViewById(R.id.state);
            distance = itemView.findViewById(R.id.distance);
            price = itemView.findViewById(R.id.price);
        }
    }
}
