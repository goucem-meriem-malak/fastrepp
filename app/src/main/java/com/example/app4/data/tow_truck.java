package com.example.app4.data;

import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;
import java.util.Map;

public class tow_truck {
    private String id, name;
    private ArrayList<vehicule_type> address;
    private GeoPoint location;

    public tow_truck() {
    }

    public tow_truck(String id, String name, ArrayList<vehicule_type> address, GeoPoint location) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<vehicule_type> getAddress() {
        return address;
    }

    public void setAddress(ArrayList<vehicule_type> address) {
        this.address = address;
    }

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }
}
