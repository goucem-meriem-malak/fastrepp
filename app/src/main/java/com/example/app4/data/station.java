package com.example.app4.data;

import com.google.firebase.firestore.GeoPoint;

import java.util.Map;

public class station {
    private String id,name, dunit;
    private GeoPoint location;
    private float distance;
    private boolean available;
    private Map<String, Object> address;

    public station() {
    }

    public station(String id, String name, String dunit, GeoPoint location, float distance, boolean available, Map<String, Object> address) {
        this.id = id;
        this.name = name;
        this.dunit = dunit;
        this.location = location;
        this.distance = distance;
        this.available = available;
        this.address = address;
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

    public String getDunit() {
        return dunit;
    }

    public void setDunit(String dunit) {
        this.dunit = dunit;
    }

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Map<String, Object> getAddress() {
        return address;
    }

    public void setAddress(Map<String, Object> address) {
        this.address = address;
    }
}
