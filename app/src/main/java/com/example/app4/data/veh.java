package com.example.app4.data;

public class veh {
    private String[] type = new String[]{"Passenger Vehicule", "Bus", "Tractor", "Trailer", "Vans", "Truck"};
    private String[] Passenger_vehicule = new String[]{"Audi", "BMW", "Dacia", "Chevrolet", "Dacia", "Hyundai", "Nissan","Skoda", "Volkswagen", "Toyota", "Seat", "Kia"};
    private String[] nissan = new String[]{"Micra", "Sunny"};
    private String[] hyunda = new String[]{"Accent"};

    public veh() {
    }

    public veh(String[] type, String[] passenger_vehicule, String[] nissan, String[] hyunda) {
        this.type = type;
        Passenger_vehicule = passenger_vehicule;
        this.nissan = nissan;
        this.hyunda = hyunda;
    }

    public String[] getType() {
        return type;
    }

    public void setType(String[] type) {
        this.type = type;
    }

    public String[] getPassenger_vehicule() {
        return Passenger_vehicule;
    }

    public void setPassenger_vehicule(String[] passenger_vehicule) {
        Passenger_vehicule = passenger_vehicule;
    }

    public String[] getNissan() {
        return nissan;
    }

    public void setNissan(String[] nissan) {
        this.nissan = nissan;
    }

    public String[] getHyunda() {
        return hyunda;
    }

    public void setHyunda(String[] hyunda) {
        this.hyunda = hyunda;
    }
}
