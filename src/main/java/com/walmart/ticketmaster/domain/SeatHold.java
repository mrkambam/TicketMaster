package com.walmart.ticketmaster.domain;

import java.util.List;

/**
 * Created by mkambam on 12/14/15.
 */
//@Setter
//@Getter
public class SeatHold {

    private final Integer id;

    private final String mailId;

    private final Long timer;

    private List<Seat> seats;


    public SeatHold(Integer seatHoldId, String customerMailId, Long durationToHold) {
        id = seatHoldId;
        mailId = customerMailId;
        timer = durationToHold;
    }

    public Integer getId() {
        return id;
    }

    public String getMailId() {
        return mailId;
    }

    public Long getTimer() {
        return timer;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }
}
