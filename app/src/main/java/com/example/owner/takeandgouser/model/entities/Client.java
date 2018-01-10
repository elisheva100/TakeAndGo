package com.example.owner.takeandgouser.model.entities;

import java.util.Date;

/**
 * Created by Owner on 08/11/2017.
 */

public class Client {
    private String firstName;
    private String lastName;
    private String id;
    private String email;
    private String cellphoneNumber;
    private long creditCard;
    private Date birthday;

    public Client() {

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(long creditCard) {
        this.creditCard = creditCard;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }


    public String getCellphoneNumber() { return cellphoneNumber; }

    public void setCellphoneNumber(String cellphoneNumber) { this.cellphoneNumber = cellphoneNumber; }

    public Client(String firstName, String lastName, String id, String email, long creditCard, Date birthday) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.email = email;
        this.creditCard = creditCard;
        this.birthday = birthday;
    }
}
