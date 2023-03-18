package com.example.app4.data;

import com.google.firebase.firestore.GeoPoint;

import java.util.Map;

public class client {
    private String id,phone, firstname, lastname, email,country,state,city;
    private GeoPoint location;
    private Map<String, Object> address;
    public client() {
    }

    public client(String id, String phone, String firstname, String lastname, String email, GeoPoint location, Map<String, Object> address) {
        this.id = id;
        this.phone = phone;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.location = location;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    public Map<String, Object> getAddress() {
        return address;
    }

    public void setAddress(Map<String, Object> address) {
        this.address = address;
    }
}
