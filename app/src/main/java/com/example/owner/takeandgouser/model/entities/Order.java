package com.example.owner.takeandgouser.model.entities;

import java.util.Date;

/**
 * Created by Owner on 08/11/2017.
 */

public class Order {
    private String clientId;
    private boolean open;
    private int carNumber;
    private Date rentStart;
    private Date rentEnd;
    private double mileageStart;
    private double mileageEnd;
    private boolean gasFilled;
    private double gasLiters;
    private double finalBilling;
    private int orderNumber;

    public Order() {

    }

    public String getClientId() { return clientId; }

    public void setClientId(String clientId) {this.clientId = clientId;}

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public int getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(int carNumber) {
        this.carNumber = carNumber;
    }

    public Date getRentStart() {
        return rentStart;
    }

    public void setRentStart(Date rentStart) {
        this.rentStart = rentStart;
    }

    public Date getRentEnd() {
        return rentEnd;
    }

    public void setRentEnd(Date rentEnd) {
        this.rentEnd = rentEnd;
    }

    public double getMileageStart() {
        return mileageStart;
    }

    public void setMileageStart(double mileageStart) {
        this.mileageStart = mileageStart;
    }

    public double getMileageEnd() {
        return mileageEnd;
    }

    public void setMileageEnd(double mileageEnd) {
        this.mileageEnd = mileageEnd;
    }

    public boolean isGasFilled() {
        return gasFilled;
    }

    public void setGasFilled(boolean gasFilled) {
        this.gasFilled = gasFilled;
    }

    public double getGasLiters() {
        return gasLiters;
    }

    public void setGasLiters(double gasLiters) {
        this.gasLiters = gasLiters;
    }

    public double getFinalBilling() {
        return finalBilling;
    }

    public void setFinalBilling(double finalBilling) {
        this.finalBilling = finalBilling;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Order(String clientId, boolean open, int carNumber, Date rentStart, Date rentEnd, double mileageStart, double mileageEnd, boolean gasFilled, double gasLiters, double finalBilling, int orderNumber) {
        this.clientId = clientId;
        this.open = open;
        this.carNumber = carNumber;
        this.rentStart = rentStart;
        this.rentEnd = rentEnd;
        this.mileageStart = mileageStart;
        this.mileageEnd = mileageEnd;
        this.gasFilled = gasFilled;
        this.gasLiters = gasLiters;
        this.finalBilling = finalBilling;
        this.orderNumber = orderNumber;
    }
}
