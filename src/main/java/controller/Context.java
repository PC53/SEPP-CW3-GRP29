package controller;

import external.MockPaymentSystem;
import external.PaymentSystem;
import state.*;

public class Context extends Object{
    private PaymentSystem mockPaymentSystem;
    private IUserState userState;
    private IEventState eventState;
    private IBookingState bookingState;
    private ISponsorshipState sponsorshipState;

    private ISponsorshipState attribute;

    public Context(){
        this.mockPaymentSystem = new MockPaymentSystem();
        this.userState = new UserState();
        this.eventState = new EventState();
        this.bookingState = new BookingState();
        this.sponsorshipState = new SponsorshipState();

    }

    public Context(Context other){
        this.mockPaymentSystem = other.getPaymentSystem();

    }

    public PaymentSystem getPaymentSystem(){
        return mockPaymentSystem;
    }

    public IUserState getUserState(){
        return userState;
    }

    public IBookingState getBookingState(){
        return bookingState;
    }

    public IEventState getEventState(){
        return eventState;
    }

    public ISponsorshipState getSponsorshipState(){
        return sponsorshipState;
    }

}
