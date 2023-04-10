package com.example.app4.data;

import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;
import java.util.Map;

public class tow_truck {
    private String id, firstname, lastname, phone;
    private ArrayList<vehicule_type> address;
    private GeoPoint location;

    public tow_truck() {
    }

    public tow_truck(String id, String firstname, String lastname, String phone, ArrayList<vehicule_type> address, GeoPoint location) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
        this.address = address;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
