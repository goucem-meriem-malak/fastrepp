package com.example.app4.data;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;
import java.util.Map;

public class request {
    private String id, client_id, mechanic_id, station_id, garage_id, taxi_id, type, state, dunit, oil_type, fuel_type,
    oil_unit, fuel_unit;
    boolean fuel, oil, taxi, ambulance;
    private int passenger_number, team_nbr;
    private Timestamp date;
    private float price, distance;
    private GeoPoint client_location, mechanic_location, station_location, garage_location, taxi_location;
    private Map<String, Object> address;
    private veh vehicle;

    public request() {
    }

    public request(String id, String client_id, String mechanic_id, String station_id, String garage_id, String taxi_id, String type, String state, String dunit, String oil_type, String fuel_type, String oil_unit, String fuel_unit, boolean fuel, boolean oil, boolean taxi, boolean ambulance, int passenger_number, int team_nbr, Timestamp date, float price, float distance, GeoPoint client_location, GeoPoint mechanic_location, GeoPoint station_location, GeoPoint garage_location, GeoPoint taxi_location, Map<String, Object> address, veh vehicle) {
        this.id = id;
        this.client_id = client_id;
        this.mechanic_id = mechanic_id;
        this.station_id = station_id;
        this.garage_id = garage_id;
        this.taxi_id = taxi_id;
        this.type = type;
        this.state = state;
        this.dunit = dunit;
        this.oil_type = oil_type;
        this.fuel_type = fuel_type;
        this.oil_unit = oil_unit;
        this.fuel_unit = fuel_unit;
        this.fuel = fuel;
        this.oil = oil;
        this.taxi = taxi;
        this.ambulance = ambulance;
        this.passenger_number = passenger_number;
        this.team_nbr = team_nbr;
        this.date = date;
        this.price = price;
        this.distance = distance;
        this.client_location = client_location;
        this.mechanic_location = mechanic_location;
        this.station_location = station_location;
        this.garage_location = garage_location;
        this.taxi_location = taxi_location;
        this.address = address;
        this.vehicle = vehicle;
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

    public String getStation_id() {
        return station_id;
    }

    public void setStation_id(String station_id) {
        this.station_id = station_id;
    }

    public String getGarage_id() {
        return garage_id;
    }

    public void setGarage_id(String garage_id) {
        this.garage_id = garage_id;
    }

    public String getTaxi_id() {
        return taxi_id;
    }

    public void setTaxi_id(String taxi_id) {
        this.taxi_id = taxi_id;
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

    public String getDunit() {
        return dunit;
    }

    public void setDunit(String dunit) {
        this.dunit = dunit;
    }

    public String getOil_type() {
        return oil_type;
    }

    public void setOil_type(String oil_type) {
        this.oil_type = oil_type;
    }

    public String getFuel_type() {
        return fuel_type;
    }

    public void setFuel_type(String fuel_type) {
        this.fuel_type = fuel_type;
    }

    public String getOil_unit() {
        return oil_unit;
    }

    public void setOil_unit(String oil_unit) {
        this.oil_unit = oil_unit;
    }

    public String getFuel_unit() {
        return fuel_unit;
    }

    public void setFuel_unit(String fuel_unit) {
        this.fuel_unit = fuel_unit;
    }

    public boolean isFuel() {
        return fuel;
    }

    public void setFuel(boolean fuel) {
        this.fuel = fuel;
    }

    public boolean isOil() {
        return oil;
    }

    public void setOil(boolean oil) {
        this.oil = oil;
    }

    public boolean isTaxi() {
        return taxi;
    }

    public void setTaxi(boolean taxi) {
        this.taxi = taxi;
    }

    public boolean isAmbulance() {
        return ambulance;
    }

    public void setAmbulance(boolean ambulance) {
        this.ambulance = ambulance;
    }

    public int getPassenger_number() {
        return passenger_number;
    }

    public void setPassenger_number(int passenger_number) {
        this.passenger_number = passenger_number;
    }

    public int getTeam_nbr() {
        return team_nbr;
    }

    public void setTeam_nbr(int team_nbr) {
        this.team_nbr = team_nbr;
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

    public GeoPoint getStation_location() {
        return station_location;
    }

    public void setStation_location(GeoPoint station_location) {
        this.station_location = station_location;
    }

    public GeoPoint getGarage_location() {
        return garage_location;
    }

    public void setGarage_location(GeoPoint garage_location) {
        this.garage_location = garage_location;
    }

    public GeoPoint getTaxi_location() {
        return taxi_location;
    }

    public void setTaxi_location(GeoPoint taxi_location) {
        this.taxi_location = taxi_location;
    }

    public Map<String, Object> getAddress() {
        return address;
    }

    public void setAddress(Map<String, Object> address) {
        this.address = address;
    }

    public veh getVehicle() {
        return vehicle;
    }

    public void setVehicle(veh vehicle) {
        this.vehicle = vehicle;
    }
}
