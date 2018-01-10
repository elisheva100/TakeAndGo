package com.example.owner.takeandgouser.model.entities;

/**
 * Created by Owner on 08/11/2017.
 */

public class Branch {
    private Adress adress;
    private int parking;
    private int branchNumber;

    public Branch() {
        
    }

    public Adress getAdress() {
        return adress;
    }

    public void setAdress(Adress adress) {
        this.adress = adress;
    }

    public int getParking() {
        return parking;
    }

    public void setParking(int parking) {
        this.parking = parking;
    }

    public int getBranchNumber() {
        return branchNumber;
    }

    public void setBranchNumber(int branchNumber) {
        this.branchNumber = branchNumber;
    }

    public Branch(Adress adress, int parking, int branchNumber) {
        this.adress = adress;
        this.parking = parking;
        this.branchNumber = branchNumber;
    }

    public void setAdress(String asString) {

    }

}
