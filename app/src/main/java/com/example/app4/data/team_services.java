package com.example.app4.data;

import java.util.ArrayList;

public class team_services {
    public String[] services = new String[]{"Oil Changes", "Battery Replacement", "Brake Services", "Tire Services", "Belt and Hose Replacement",
            "Spark Plug Replacement", "Electrical services", "Cooling System Services"};
    private ArrayList team_services;

    public team_services() {
    }

    public team_services(String[] services, ArrayList team_services) {
        this.services = services;
        this.team_services = team_services;
    }

    public String[] getServices() {
        return services;
    }

    public void setServices(String[] services) {
        this.services = services;
    }

    public ArrayList getTeam_services() {
        return team_services;
    }

    public void setTeam_services(ArrayList team_services) {
        this.team_services = team_services;
    }
}