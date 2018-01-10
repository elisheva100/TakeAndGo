package com.example.owner.takeandgouser.model.entities;

/**
 * Created by Owner on 08/11/2017.
 */

public class Adress {
    private String city;
    private String street;
    private int number;

    public Adress() {

    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Adress(String city, String street, int number) {
        this.city = city;
        this.street = street;
        this.number = number;
    }

    @Override
    public String toString() {
        return this.street + " " + this.number + " " + this.city;
    }
}
