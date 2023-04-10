package com.example.app4.data;

public class oil_fuel {
    private String[] oil=new String[]{ "Synthetic", "Synthetic Blends", "High Mileage", "Conventional"};
    private String[] fuel=new String[]{ "Gasoline", "Diesel", "LPG", "Ethanol"};

    public oil_fuel() {
    }

    public oil_fuel(String[] oil, String[] fuel) {
        this.oil = oil;
        this.fuel = fuel;
    }

    public String[] getOil() {
        return oil;
    }

    public void setOil(String[] oil) {
        this.oil = oil;
    }

    public String[] getFuel() {
        return fuel;
    }

    public void setFuel(String[] fuel) {
        this.fuel = fuel;
    }
}
