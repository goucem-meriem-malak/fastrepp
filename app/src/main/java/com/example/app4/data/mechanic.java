package com.example.app4.data;

import com.google.firebase.firestore.GeoPoint;

public class mechanic {
    private String id,name;
    private boolean free;
    private GeoPoint location;

    public mechanic() {
    }

    public mechanic(String id, String name, boolean free, GeoPoint location) {
        this.id = id;
        this.name = name;
        this.free = free;
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

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }
}
