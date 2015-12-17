package com.walmart.ticketmaster.service.impl;

import com.walmart.ticketmaster.domain.Seat;
import com.walmart.ticketmaster.domain.SeatHold;
import com.walmart.ticketmaster.exception.InvalidVenueLevelException;
import com.walmart.ticketmaster.exception.MissingCustomerEmailException;
import com.walmart.ticketmaster.exception.MissingSeatsToHoldException;
import com.walmart.ticketmaster.repository.SeatRepository;
import com.walmart.ticketmaster.service.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by mkambam on 12/12/15.
 */
@Service
@Qualifier("ticketService")
public class TicketServiceImpl implements TicketService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TicketServiceImpl.class);
    //CHECKSTYLE IGNORE MagicNumber FOR NEXT LINE
    private static int holdDuration = 60 * 1000;

    @Autowired
    private SeatRepository seatRepository;

    @Override
    public int numSeatsAvailable(Optional<Integer> venueLevel) {
        if (venueLevel.isPresent()) {
            int venueLevelId = venueLevel.get();
            validateVenueLevelId(venueLevelId);
            return seatRepository.getAvailableSeatsByVenueLevelId(venueLevelId);
        } else {
            return seatRepository.getAllAvailableSeats();
        }
    }

    @Override
    public SeatHold findAndHoldSeats(int numSeats, Optional<Integer> minLevel,
                                     Optional<Integer> maxLevel, String customerEmail) {
        SeatHold seatHold = null;
        int min = 1;
        int max = 4;
        if (minLevel.isPresent()) {
            validateVenueLevelId(minLevel.get());
            min = minLevel.get();
        }

        if (maxLevel.isPresent()) {
            validateVenueLevelId(maxLevel.get());
            max = minLevel.get();

        }

        if (numSeats <= 0) {
            throw new MissingSeatsToHoldException("Number of Seats to hold is required");
        }
        if (customerEmail.isEmpty()) {
            throw new MissingCustomerEmailException("Customer Email against whom Seats need to be held is required");
        }
        long durationToHold = System.currentTimeMillis() + holdDuration;
        List<Seat> holdSeats = new ArrayList<>();

        int requiredSeats = numSeats;
        for (int i = min; i <= max; i++) {
            List<Seat> atLevel = seatRepository.findSeatsToHold(requiredSeats, i, durationToHold);
            requiredSeats -= atLevel.size();
            holdSeats.addAll(atLevel);
            if (requiredSeats <= 0) {
                break;
            }
        }
        if (requiredSeats <= 0) {
            seatHold = seatRepository.holdSeats(holdSeats, customerEmail, durationToHold);
        }
        return seatHold;
    }

    @Override
    public String reserveSeats(int seatHoldId, String customerEmail) {

        if (seatHoldId <= 0 || customerEmail.isEmpty()) {
            return null;
        }

        return seatRepository.reserveSeats(seatHoldId, customerEmail) ? "SUCCESS" : "FAILURE";
    }

    private void validateVenueLevelId(Integer venueLevelId) {
        //CHECKSTYLE IGNORE MagicNumber FOR NEXT LINE
        if (venueLevelId < 1 || venueLevelId > 4) {
            throw new InvalidVenueLevelException("Valid values for VenueLevelEnum are 1,2,3 or 4");
        }
    }
}
