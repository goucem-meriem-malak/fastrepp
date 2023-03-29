package com.example.app4.data;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

public class request {
    private String id, client_id, mechanic_id, id_station, id_garage, id_oil, id_fuel, type, state;
    private Timestamp date;
    private float price, distance;
    private GeoPoint client_location, mechanic_location;
    private Map<String, Object> address;
    private ArrayList<vehicule_type> vehicule;
    private ArrayList<fuel> fuel;
    private ArrayList<oil> oil;

    public request() {
    }

    public request(String id, String client_id, String mechanic_id, String id_station, String id_garage, String id_oil, String id_fuel, String type, String state, Timestamp date, float price, float distance, GeoPoint client_location, GeoPoint mechanic_location, Map<String, Object> address, ArrayList<vehicule_type> vehicule, ArrayList<com.example.app4.data.fuel> fuel, ArrayList<com.example.app4.data.oil> oil) {
        this.id = id;
        this.client_id = client_id;
        this.mechanic_id = mechanic_id;
        this.id_station = id_station;
        this.id_garage = id_garage;
        this.id_oil = id_oil;
        this.id_fuel = id_fuel;
        this.type = type;
        this.state = state;
        this.date = date;
        this.price = price;
        this.distance = distance;
        this.client_location = client_location;
        this.mechanic_location = mechanic_location;
        this.address = address;
        this.vehicule = vehicule;
        this.fuel = fuel;
        this.oil = oil;
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

    public String getId_station() {
        return id_station;
    }

    public void setId_station(String id_station) {
        this.id_station = id_station;
    }

    public String getId_garage() {
        return id_garage;
    }

    public void setId_garage(String id_garage) {
        this.id_garage = id_garage;
    }

    public String getId_oil() {
        return id_oil;
    }

    public void setId_oil(String id_oil) {
        this.id_oil = id_oil;
    }

    public String getId_fuel() {
        return id_fuel;
    }

    public void setId_fuel(String id_fuel) {
        this.id_fuel = id_fuel;
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

    public Map<String, Object> getAddress() {
        return address;
    }

    public void setAddress(Map<String, Object> address) {
        this.address = address;
    }

    public ArrayList<vehicule_type> getVehicule() {
        return vehicule;
    }

    public void setVehicule(ArrayList<vehicule_type> vehicule) {
        this.vehicule = vehicule;
    }

    public ArrayList<com.example.app4.data.fuel> getFuel() {
        return fuel;
    }

    public void setFuel(ArrayList<com.example.app4.data.fuel> fuel) {
        this.fuel = fuel;
    }

    public ArrayList<com.example.app4.data.oil> getOil() {
        return oil;
    }

    public void setOil(ArrayList<com.example.app4.data.oil> oil) {
        this.oil = oil;
    }
}
