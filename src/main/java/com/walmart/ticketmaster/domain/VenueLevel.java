package com.walmart.ticketmaster.domain;

import java.util.List;

/**
 * Created by mkambam on 12/14/15.
 */
public class VenueLevel {

    private Integer levelId;

    private String name;

    private Double price;

    private List<Seat> seats;

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }
}
