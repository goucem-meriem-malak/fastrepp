package com.example.app4.data;

public class vehicle {
    private String id, matriculation, mark, type, model;

    public vehicle() {
    }

    public vehicle(String mark, String type, String model) {
        this.mark = mark;
        this.type = type;
        this.model = model;
    }

    public vehicle(String id, String matriculation, String mark, String type, String model) {
        this.id = id;
        this.matriculation = matriculation;
        this.mark = mark;
        this.type = type;
        this.model = model;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMatriculation() {
        return matriculation;
    }

    public void setMatriculation(String matriculation) {
        this.matriculation = matriculation;
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
}
