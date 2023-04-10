package com.example.app4;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app4.data.get_requests;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        com.example.app4.data.get_requests request = get_requests.get(position);
        holder.price.setText(String.valueOf(request.getPrice()));
        holder.state.setText(request.getState());
        if(holder.state.getText().equals("waiting")){
            holder.buttons.setVisibility(View.VISIBLE);
        }
        else {
            holder.buttons.setVisibility(View.INVISIBLE);
        }
        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.state.setText("rejected");
                Map m = new HashMap();
                m.put("state", "rejected");
                db.collection("mechanic").document(auth.getUid()).update(m);
            }
        });
        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.state.setText("Ongoing");
                Map m = new HashMap();
                m.put("state", "Ongoing");
                db.collection("mechanic").document(auth.getUid()).update(m);
            }
        });
    }

    @Override
    public int getItemCount() {
        return get_requests.size();
    }

    public static class myviewholder extends RecyclerView.ViewHolder{
        TextView id, client_id, mechanic_id, client_address, mechanic_location, type, state, price, distance;
        LinearLayout buttons;
        Button reject, accept;
        public myviewholder(@NonNull View itemView) {
            super(itemView);

            reject = itemView.findViewById(R.id.reject);
            accept = itemView.findViewById(R.id.accept);
            buttons = itemView.findViewById(R.id.buttons);
            client_address = itemView.findViewById(R.id.address);
            state = itemView.findViewById(R.id.state);
            price = itemView.findViewById(R.id.price);
        }
    }
}
