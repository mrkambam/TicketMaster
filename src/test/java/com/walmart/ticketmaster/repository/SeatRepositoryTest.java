package com.walmart.ticketmaster.repository;

import com.walmart.ticketmaster.config.AppConfig;
import com.walmart.ticketmaster.constant.VenueLevelEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

/**
 * Created by mkambam on 12/16/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class SeatRepositoryTest {

    @Autowired
    private SeatRepository seatRepository;

    @Test
    public void testGetAvailableSeatsByVenueLevelId() {
        int orchestraVenueSeatCount = 25 * 50;
        int seatCountInOrchestraVenue = seatRepository.getAvailableSeatsByVenueLevelId(VenueLevelEnum.ORCHESTRA.getLevelId());
        assertEquals(orchestraVenueSeatCount, seatCountInOrchestraVenue);
    }
}
