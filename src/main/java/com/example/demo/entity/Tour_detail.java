package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;

@Entity
public class Tour_detail {
    @Id
    private int id_tourdetail;
    private String name;
    private java.sql.Date startDay;
    private java.sql.Date endDay;
    private int price;
    private int quantity_ticket;
    private int remaining_ticket;
    private String departure;
    private String description;
    private String image;
    private int status;

    public Tour_detail() {
    }

    public Tour_detail(int id_tourdetail, String name, Date startDay, Date endDay, int price, int quantity_ticket, int remaining_ticket, String departure, String description, String image, int status) {
        this.id_tourdetail = id_tourdetail;
        this.name = name;
        this.startDay = startDay;
        this.endDay = endDay;
        this.price = price;
        this.quantity_ticket = quantity_ticket;
        this.remaining_ticket = remaining_ticket;
        this.departure = departure;
        this.description = description;
        this.image = image;
        this.status = status;
    }

    public int getId_tourdetail() {
        return id_tourdetail;
    }

    public void setId_tourdetail(int id_tourdetail) {
        this.id_tourdetail = id_tourdetail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDay() {
        return startDay;
    }

    public void setStartDay(Date startDay) {
        this.startDay = startDay;
    }

    public Date getEndDay() {
        return endDay;
    }

    public void setEndDay(Date endDay) {
        this.endDay = endDay;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity_ticket() {
        return quantity_ticket;
    }

    public void setQuantity_ticket(int quantity_ticket) {
        this.quantity_ticket = quantity_ticket;
    }

    public int getRemaining_ticket() {
        return remaining_ticket;
    }

    public void setRemaining_ticket(int remaining_ticket) {
        this.remaining_ticket = remaining_ticket;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
