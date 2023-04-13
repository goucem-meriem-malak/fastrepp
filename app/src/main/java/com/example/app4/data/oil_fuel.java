package com.example.app4.data;

public class oil_fuel {
    private String oill, fuell;
    private String[] oil=new String[]{ "Synthetic", "Synthetic Blends", "High Mileage", "Conventional"};
    private String[] fuel=new String[]{ "Gasoline", "Diesel", "LPG", "Ethanol"};

    public oil_fuel() {
    }

    public oil_fuel(String oill, String fuell, String[] oil, String[] fuel) {
        this.oill = oill;
        this.fuell = fuell;
        this.oil = oil;
        this.fuel = fuel;
    }

    public String getOill() {
        return oill;
    }

    public void setOill(String oill) {
        this.oill = oill;
    }

    public String getFuell() {
        return fuell;
    }

    public void setFuell(String fuell) {
        this.fuell = fuell;
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
