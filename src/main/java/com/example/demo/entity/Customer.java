package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Customer {
    @Id
    private int id_customer;
    private String name;
//    @NotEmpty(message = "*Please provide an email")
    private String email;
    private int telephone;
//    @Length(min = 5, message = "*Your password must have at least 5 characters")
    private String password;
    private int status;

    public Customer() {
    }

    public Customer(String name, String email, int telephone, String password, int status) {
        this.name = name;
        this.email = email;
        this.telephone = telephone;
        this.password = password;
        this.status = status;
    }

    public int getId_customer() {
        return id_customer;
    }

    public void setId_customer(int id_customer) {
        this.id_customer = id_customer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTelephone() {
        return telephone;
    }

    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
