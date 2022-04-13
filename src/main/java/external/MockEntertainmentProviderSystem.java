package external;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MockEntertainmentProviderSystem extends Object implements EntertainmentProviderSystem{

    private Map<Long, Integer> ticketsAvailable;
    private Map<Long, List<Long>> performancesOfEvent;
    private Map<Long, Integer> bookedTicketsForBookings;
    private Map<Long, Long> bookedEventNumberForBookings;

    private final String orgName;
    private final String orgAddress;

    public MockEntertainmentProviderSystem(String orgName, String orgAddress) {
        this.orgName = orgName;
        this.orgAddress = orgAddress;
        this.ticketsAvailable = new HashMap<Long, Integer>();
        this.performancesOfEvent = new HashMap<Long, List<Long>>();
        this.bookedTicketsForBookings = new HashMap<Long, Integer>();
        this.bookedEventNumberForBookings = new HashMap<Long, Long>();
    }

    @Override
    public void cancelBooking(long bookingNumber) {
        if (bookedEventNumberForBookings.get(bookingNumber) != null) {
            int bookedTickets = bookedTicketsForBookings.get(bookingNumber);
            long bookedEventNumber = bookedEventNumberForBookings.get(bookingNumber);
            int ticketsAvailableForEvent = ticketsAvailable.get(bookedEventNumber);
            int updatedTicketsAvailableForEvent = ticketsAvailableForEvent + bookedTickets;
            ticketsAvailable.replace(bookedEventNumber, updatedTicketsAvailableForEvent);
            bookedTicketsForBookings.remove(bookingNumber);
            bookedEventNumberForBookings.remove(bookingNumber);
            System.out.println("The booking with Booking Number " + bookingNumber + " is cancelled.");
        } else {
            System.out.println("The booking with Booking Number " + bookingNumber + " does not existed.");
        }
    }

    @Override
    public void cancelEvent(long eventNumber, String message) {
        if (ticketsAvailable.get(eventNumber) != null) {
            ticketsAvailable.remove(eventNumber);
            performancesOfEvent.remove(eventNumber);
            Set<Long> bookingNumberSet = bookedEventNumberForBookings.keySet();
            for (Long tempBookingNumber : bookingNumberSet) {
                if (bookedEventNumberForBookings.get(tempBookingNumber) == eventNumber) {
                    bookedTicketsForBookings.remove(tempBookingNumber);
                    bookedEventNumberForBookings.remove(tempBookingNumber);
                }
            }
            System.out.println("The event with Event Number " + eventNumber + " is cancelled and the message [" + message + "] has been sent to all the bookers.");
        } else {
            System.out.println("The event with Event Number " + eventNumber + " does not existed.");
        }
    }

    @Override
    public int getNumTicketsLeft(long eventNumber, long performanceNumber) {
        if (performancesOfEvent.get(eventNumber).contains(performanceNumber)) {
            return ticketsAvailable.get(eventNumber);
        }
        return 0;
    }

    @Override
    public void recordNewBooking(long eventNumber, long performanceNumber, long bookingNumber, String consumerName, String consumerEmail, int bookedTickets) {
        if (bookedEventNumberForBookings.get(bookingNumber) == null) {
            int ticketsAvailableForEvent = ticketsAvailable.get(eventNumber);
            if (ticketsAvailableForEvent >= bookedTickets) {
                int updatedTicketsAvailableForEvent = ticketsAvailableForEvent - bookedTickets;
                ticketsAvailable.replace(eventNumber, updatedTicketsAvailableForEvent);
                bookedTicketsForBookings.put(bookingNumber, bookedTickets);
                bookedEventNumberForBookings.put(bookingNumber, eventNumber);
                System.out.print("A new booking with Booking Number " + bookingNumber + " for the performance with Performance Number " + performanceNumber + " and Event Number " + eventNumber + " has been made successfully.");
                System.out.println("The name of the consumer is " + consumerName + " and his/her email is " + consumerEmail + ".");
            } else {
                System.out.println("A new booking made with Booking Number " + bookingNumber + " has failed due to the limited tickets for the performance with Performance Number " + performanceNumber + " and Event Number " + eventNumber + ".");
            }
        } else {
            System.out.println("A booking with the same Booking Number " + bookingNumber + " is already existed.");
        }
    }

    @Override
    public void recordNewEvent(long eventNumber, String title, int numTickets) {
        if (ticketsAvailable.get(eventNumber) == null) {
            ticketsAvailable.put(eventNumber, numTickets);
            System.out.println("A new event called " + title + " with Event Number " + eventNumber + " has been added successfully.");
        } else {
            System.out.println("An event with the same Event Number " + eventNumber + " is already existed.");
        }
    }

    @Override
    public void recordNewPerformance(long eventNumber, long performanceNumber, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        if (!performancesOfEvent.get(eventNumber).contains(performanceNumber)) {
            performancesOfEvent.get(eventNumber).add(performanceNumber);
            System.out.print("A new performance with Performance Number " + performanceNumber + " for the event with Event Number " + eventNumber + " has been added successfully.");
            System.out.println(" The performance will start at " + startDateTime + " and end at " + endDateTime + ".");
        } else {
            System.out.println("A performance with the same Performance Number " + performanceNumber + " is already existed in the event with Event Number " + eventNumber + ".");
        }
    }

    @Override
    public void recordSponsorshipAcceptance(long eventNumber, int sponsoredPricePercent) {
        System.out.println("The sponsorship request for the event with Event Number " + eventNumber + " has been accepted with a sponsorship percent of " + sponsoredPricePercent + "%.");
    }

    @Override
    public void recordSponsorshipRejection(long eventNumber) {
        System.out.println("The sponsorship request for the event with Event Number " + eventNumber + " has been rejected.");
    }
}
