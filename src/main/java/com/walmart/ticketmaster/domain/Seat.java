package com.walmart.ticketmaster.domain;

import com.walmart.ticketmaster.constant.SeatStatus;

/**
 * Created by mkambam on 12/14/15.
 */
public class Seat {
    private Integer id;

    private SeatStatus status;

    private Integer venueLevelId;

    private Integer seatHoldId;

    private Long timer = System.currentTimeMillis();

    public boolean isAvailable() {
        return status != SeatStatus.RESERVED && timer < System.currentTimeMillis();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SeatStatus getStatus() {
        return status;
    }

    public void setStatus(SeatStatus status) {
        this.status = status;
    }

    public Integer getVenueLevelId() {
        return venueLevelId;
    }

    public void setVenueLevelId(Integer venueLevelId) {
        this.venueLevelId = venueLevelId;
    }

    public Integer getSeatHoldId() {
        return seatHoldId;
    }

    public void setSeatHoldId(Integer seatHoldId) {
        this.seatHoldId = seatHoldId;
    }

    public Long getTimer() {
        return timer;
    }

    public void setTimer(Long timer) {
        this.timer = timer;
    }
}
