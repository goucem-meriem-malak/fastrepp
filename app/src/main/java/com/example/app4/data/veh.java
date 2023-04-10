package com.example.app4.data;

public class veh {
    private String mark, type, model;
    private String[] types = new String[]{"Passenger Vehicule", "Bus", "Tractor", "Trailer", "Vans", "Truck"};
    private String[] Passenger_vehicle = new String[]{"Audi", "BMW", "Dacia", "Chevrolet", "Dacia", "Hyundai", "Nissan","Skoda", "Volkswagen", "Toyota", "Seat", "Kia"};
    private String[] nissan = new String[]{"Micra", "Sunny"};
    private String[] hyundai = new String[]{"Accent"};

    public veh() {
    }

    public veh(String mark, String type, String model) {
        this.mark = mark;
        this.type = type;
        this.model = model;
    }

    public veh(String mark, String type, String model, String[] types, String[] passenger_vehicle, String[] nissan, String[] hyundai) {
        this.mark = mark;
        this.type = type;
        this.model = model;
        this.types = types;
        Passenger_vehicle = passenger_vehicle;
        this.nissan = nissan;
        this.hyundai = hyundai;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String[] getTypes() {
        return types;
    }

    public void setTypes(String[] types) {
        this.types = types;
    }

    public String[] getPassenger_vehicle() {
        return Passenger_vehicle;
    }

    public void setPassenger_vehicle(String[] passenger_vehicle) {
        Passenger_vehicle = passenger_vehicle;
    }

    public String[] getNissan() {
        return nissan;
    }

    public void setNissan(String[] nissan) {
        this.nissan = nissan;
    }

    public String[] getHyundai() {
        return hyundai;
    }

    public void setHyundai(String[] hyundai) {
        this.hyundai = hyundai;
    }
}
