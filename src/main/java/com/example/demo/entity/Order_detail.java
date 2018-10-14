package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;

@Entity
public class Order_detail {
    @Id
    private int idOrderdetail;
    private int idTourdetail;
    private int idCustomer;
    private java.sql.Date expired_Date;
    private int quantity;
    private int price;
    private java.sql.Date order_Date;
    private int status;
//    1: chưa thanh toán, 3: đã thanh toán, 0: đã xóa, -1: het han ma chua pay

    private java.sql.Date startDay;
    private String name;
    private String image;
    private String departure;

    public Order_detail() {
    }

    public Order_detail(int idTourdetail, int idCustomer, Date expired_Date, int quantity, int price, Date order_Date, int status, Date startDay, String name, String image, String departure) {
        this.idTourdetail = idTourdetail;
        this.idCustomer = idCustomer;
        this.expired_Date = expired_Date;
        this.quantity = quantity;
        this.price = price;
        this.order_Date = order_Date;
        this.status = status;
        this.startDay = startDay;
        this.name = name;
        this.image = image;
        this.departure = departure;
    }

    public int getIdOrderdetail() {
        return idOrderdetail;
    }

    public void setIdOrderdetail(int idOrderdetail) {
        this.idOrderdetail = idOrderdetail;
    }

    public int getIdTourdetail() {
        return idTourdetail;
    }

    public void setIdTourdetail(int idTourdetail) {
        this.idTourdetail = idTourdetail;
    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public Date getExpired_Date() {
        return expired_Date;
    }

    public void setExpired_Date(Date expired_Date) {
        this.expired_Date = expired_Date;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getOrder_Date() {
        return order_Date;
    }

    public void setOrder_Date(Date order_Date) {
        this.order_Date = order_Date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getStartDay() {
        return startDay;
    }

    public void setStartDay(Date startDay) {
        this.startDay = startDay;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }
}
