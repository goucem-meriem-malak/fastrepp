package com.example.app4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app4.data.request;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class adapter_request extends RecyclerView.Adapter<adapter_request.myviewholder> {
    Context context;
    ArrayList<request> get_requests;
    private double distance;

    public adapter_request(Context context, ArrayList<request> get_requests) {
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
        request request = get_requests.get(position);
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
            if (request.getType().equals("mechanic")){
                holder.p.setVisibility(View.GONE);
                holder.price.setVisibility(View.GONE);
                if (request.getVehicle()==null){
                    holder.veh_type.setText("");
                    holder.veh_mark.setText("");
                    holder.veh_model.setText("");
                } else {
                    if (request.getVehicle().getType()==null){
                        holder.veh_type.setText("");
                    } else {
                        holder.veh_type.setText(request.getVehicle().getType());
                    }
                    if (request.getVehicle().getMark()==null){
                        holder.veh_mark.setText("");
                    } else {
                        holder.veh_mark.setText(request.getVehicle().getMark());
                    }
                    if (request.getVehicle().getModel()==null){
                        holder.veh_model.setText("");
                    } else {
                        holder.veh_model.setText(request.getVehicle().getModel());
                    }
                }
            }else{
            if(request.getPrice()==0){
                holder.price.setText("");
            } else {
                holder.price.setText(String.valueOf(request.getPrice()));
            }
            }
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
        TextView address, date, type, veh_mark, veh_type, veh_model, state, price, p, distance;
        LinearLayout v;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            address = itemView.findViewById(R.id.address);
            date = itemView.findViewById(R.id.date);
            type = itemView.findViewById(R.id.type);

            distance = itemView.findViewById(R.id.distance);
            state = itemView.findViewById(R.id.state);
            p = itemView.findViewById(R.id.p);
            price = itemView.findViewById(R.id.price);
            veh_type = itemView.findViewById(R.id.veh_type);
            veh_mark = itemView.findViewById(R.id.veh_mark);
            veh_model = itemView.findViewById(R.id.veh_model);
            v = itemView.findViewById(R.id.v);
        }
    }
}
