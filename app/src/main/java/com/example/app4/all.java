package com.example.app4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app4.mechanic_services.find_mechanic;
import com.example.app4.mechanic_services.mechanics_list;
import com.example.app4.others.home;

import java.util.ArrayList;
import java.util.List;

public class all extends AppCompatActivity {
   /* implements selectlistenerRecyclerView recyclerView;
    List<item> items;
    myadapter myadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all);

        recyclerView = findViewById(R.id.recycler);

        items = new ArrayList<item>();
     //   items.add(new item("hh",16,"hh"));
     //   items.add(new item("hi", 16, "hh"));
        items.add(new item("john", 16,"nn"));
        items.add(new item("john", 18,"ch"));


        myadapter= new myadapter(this, items, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myadapter);
    }

    @Override
    public void onItemClicked(item items) {
        Intent activityChangeIntent = new Intent(all.this, home.class);
        all.this.startActivity(activityChangeIntent);
    }*/
}
