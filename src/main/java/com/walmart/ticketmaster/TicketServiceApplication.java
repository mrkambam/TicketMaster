package com.walmart.ticketmaster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by mkambam on 12/14/15.
 */

@SpringBootApplication
@ComponentScan(basePackages = {"com.walmart.ticketmaster"})
public class TicketServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TicketServiceApplication.class,args);
    }
}
