package com.example.app4;

public class item {

    private String id, name;

    public item(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public item() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
