package com.example.owner.takeandgouser.model.entities;

/**
 * Created by Owner on 08/11/2017.
 */

public class CarModel {
    private int code;
    private String companyName;
    private String modelName;
    private double engineCapacity;
    private GEARBOX gearbox;
    private int seats;
    private String color;
    public CarModel() {

    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public double getEngineCapacity() {
        return engineCapacity;
    }

    public void setEngineCapacity(double engineCapacity) {
        this.engineCapacity = engineCapacity;
    }

    public GEARBOX getGearbox() {
        return gearbox;
    }

    public void setGearbox(GEARBOX gearbox) {
        this.gearbox = gearbox;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }


    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public CarModel(int code, String companyName, String modelName, double engineCapacity, GEARBOX gearbox, int seats, String color) {
        this.code = code;
        this.companyName = companyName;
        this.modelName = modelName;
        this.engineCapacity = engineCapacity;
        this.gearbox = gearbox;
        this.seats = seats;
        this.color = color;
    }
}
