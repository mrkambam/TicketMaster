package com.walmart.ticketmaster.repository;

import com.walmart.ticketmaster.constant.SeatStatus;
import com.walmart.ticketmaster.constant.VenueLevelEnum;
import com.walmart.ticketmaster.domain.Seat;
import com.walmart.ticketmaster.domain.SeatHold;
import lombok.Synchronized;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by mkambam on 12/15/15.
 */
@Repository
public class SeatRepository {

    private static final Map<Integer, List<Seat>> SEATS = new HashMap<>();
    private static final Map<String, SeatHold> SEAT_HOLDER = new HashMap<>();
    //CHECKSTYLE IGNORE MagicNumber FOR NEXT LINE
    private static final AtomicInteger HOLD_ID = new AtomicInteger(9999);

    @PostConstruct
    public void init() {
        System.out.println("Initializing static ticketData map.");
        SEATS.put(VenueLevelEnum.ORCHESTRA.getLevelId(), getSeats(VenueLevelEnum.ORCHESTRA));
        SEATS.put(VenueLevelEnum.MAIN.getLevelId(), getSeats(VenueLevelEnum.MAIN));
        SEATS.put(VenueLevelEnum.BALCONY2.getLevelId(), getSeats(VenueLevelEnum.BALCONY2));
        SEATS.put(VenueLevelEnum.BALCONY1.getLevelId(), getSeats(VenueLevelEnum.BALCONY1));
    }

    private List<Seat> getSeats(VenueLevelEnum venueLevel) {
        List<Seat> venueSeats = new ArrayList<>(venueLevel.getTotalSeats());

        for (int i = 1; i <= venueLevel.getTotalSeats(); i++) {
            Seat seat = new Seat();
            //CHECKSTYLE IGNORE MagicNumber FOR NEXT LINE
            seat.setId(i + (1000 * venueLevel.getLevelId()));
            seat.setStatus(SeatStatus.AVAILABLE);
            seat.setVenueLevelId(venueLevel.getLevelId());
            venueSeats.add(seat);
        }
        return venueSeats;
    }

    public int getAvailableSeatsByVenueLevelId(int venueLevelId) {
        return (int) SEATS.get(venueLevelId).stream()
                .filter(seat -> seat.isAvailable()).count();
    }


    public int getAllAvailableSeats() {
        int numOfSeats = 0;
        for (VenueLevelEnum e : VenueLevelEnum.values()) {
            numOfSeats += getAvailableSeatsByVenueLevelId(e.getLevelId());
        }
        return numOfSeats;
    }


    @Synchronized
    public List<Seat> findSeatsToHold(int numSeats, int venueLevelId, long durationToHold) {
        /*return SEATS.get(venueLevelId).stream().filter(seat -> {
            int numOfSeats = 0;
            boolean available = seat.isAvailable() && numOfSeats++ <= numSeats;
            if (available) {
                seat.setTimer(durationToHold);
            }
            return available;
        }).collect(Collectors.toList());*/

        List<Seat> seatsTohold = new ArrayList<>(numSeats);
        for (Seat seat: SEATS.get(venueLevelId)) {
            if (seat.isAvailable()) {
                seat.setTimer(durationToHold);
                seat.setStatus(SeatStatus.HOLD);
                seatsTohold.add(seat);
            }

            if (seatsTohold.size() == numSeats) {
                break;
            }
        }

        return seatsTohold;
    }

    public SeatHold holdSeats(List<Seat> holdSeats, String customerEmail, long durationToHold) {
        SeatHold seatHold = new SeatHold(HOLD_ID.incrementAndGet(), customerEmail, durationToHold);
        seatHold.setSeats(holdSeats);
        SEAT_HOLDER.put(seatHold.getId() + customerEmail, seatHold);
        System.out.println("SeatHoldId for the customer: " + customerEmail + " is: " + seatHold.getId());
        return seatHold;
    }

    public boolean reserveSeats(Integer seatHoldId, String customerEmail) {
        if (SEAT_HOLDER.containsKey(seatHoldId + customerEmail)) {
            SeatHold seatHold = SEAT_HOLDER.get(seatHoldId + customerEmail);
            if (seatHold.getTimer() < System.currentTimeMillis()) {
                System.out.println("Hold duration has expired, cannot reserve SEATS for you!!!");
                SEAT_HOLDER.remove(seatHoldId + customerEmail);
                return false;
            }
            seatHold.getSeats().stream().forEach(seat -> {
                seat.setStatus(SeatStatus.RESERVED);
                for (Seat s: SEATS.get(seat.getVenueLevelId())) {
                    if (s.getId() == seat.getId()) {
                        s.setStatus(SeatStatus.RESERVED);
                        s.setTimer(null);
                    }
                }
            });
            return true;
        }
        System.err.println("Unknown or Invalid combination of seatHoldId & customerMailId!!!");
        return false;
    }

}


