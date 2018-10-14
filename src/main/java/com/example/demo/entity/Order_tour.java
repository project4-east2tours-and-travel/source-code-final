package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;

@Entity
public class Order_tour {
    @Id
    private int id;
    private int idOrderdetail;
    private java.sql.Date payDate;
    private int status;

    public Order_tour() {
    }

    public Order_tour(int id, int idOrderdetail, Date payDate, int status) {
        this.id = id;
        this.idOrderdetail = idOrderdetail;
        this.payDate = payDate;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdOrderdetail() {
        return idOrderdetail;
    }

    public void setIdOrderdetail(int idOrderdetail) {
        this.idOrderdetail = idOrderdetail;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
