package com.walmart.ticketmaster.constant;

/**
 * Created by mkambam on 12/15/15.
 */
public enum VenueLevelEnum {

    ORCHESTRA(1, 25 * 50), MAIN(2, 2 * 100), BALCONY1(3, 15 * 100), BALCONY2(4, 15 * 100);

    private final Integer levelId;
    private final Integer numOfSeats;

    VenueLevelEnum(Integer levelId, int numOfSeats) {
        this.levelId = levelId;
        this.numOfSeats = numOfSeats;
    }

    public Integer getLevelId() {
        return this.levelId;
    }

    public Integer getTotalSeats() {

        return numOfSeats;
    }

    @Override
    public String toString() {
        return String.valueOf(this.levelId);
    }
}
