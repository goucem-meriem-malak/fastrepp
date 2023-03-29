package com.example.app4.data;

import java.util.Map;

public class vehicule {
    private int id;
    private veh type;
    private int registration_num;

    public vehicule() {
    }

    public vehicule(int id, veh type, int registration_num) {
        this.id = id;
        this.type = type;
        this.registration_num = registration_num;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public veh getType() {
        return type;
    }

    public void setType(veh type) {
        this.type = type;
    }

    public int getRegistration_num() {
        return registration_num;
    }

    public void setRegistration_num(int registration_num) {
        this.registration_num = registration_num;
    }
}
