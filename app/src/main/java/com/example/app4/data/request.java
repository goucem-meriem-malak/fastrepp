package com.example.app4.data;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.GeoPoint;

import java.util.Calendar;

public class request {
    private String id, client_id, mechanic_id, type, state;
    private Timestamp date;
    private float price, distance;
    private GeoPoint client_location, mechanic_location;

    public request() {
    }

    public request(String id, String client_id, String mechanic_id, String type, String state, Timestamp date, float price, float distance, GeoPoint client_location, GeoPoint mechanic_location) {
        this.id = id;
        this.client_id = client_id;
        this.mechanic_id = mechanic_id;
        this.type = type;
        this.state = state;
        this.date = date;
        this.price = price;
        this.distance = distance;
        this.client_location = client_location;
        this.mechanic_location = mechanic_location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getMechanic_id() {
        return mechanic_id;
    }

    public void setMechanic_id(String mechanic_id) {
        this.mechanic_id = mechanic_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public GeoPoint getClient_location() {
        return client_location;
    }

    public void setClient_location(GeoPoint client_location) {
        this.client_location = client_location;
    }

    public GeoPoint getMechanic_location() {
        return mechanic_location;
    }

    public void setMechanic_location(GeoPoint mechanic_location) {
        this.mechanic_location = mechanic_location;
    }
}