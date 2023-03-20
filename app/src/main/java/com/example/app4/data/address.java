package com.example.app4.data;

public class address {
    private String country, state, city;

    public address(String country, String state, String city) {
        this.country = country;
        this.state = state;
        this.city = city;
    }

    public address() {
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
