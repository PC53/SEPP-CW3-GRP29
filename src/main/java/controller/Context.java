package controller;

import external.MockPaymentSystem;
import external.PaymentSystem;
import state.*;

public class Context extends Object {
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
        this.userState = new UserState(other.getUserState());
        this.eventState = new EventState(other.getEventState());
        this.bookingState = new BookingState(other.getBookingState());
        this.sponsorshipState = new SponsorshipState(other.getSponsorshipState());
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

    /*
    @Override
    public Context clone() {
        Context context = null;
        try {
            context = (Context) super.clone();
        } catch (CloneNotSupportedException e) {
            context = new Context();
        }
        context.mockPaymentSystem = (MockPaymentSystem) this.mockPaymentSystem.clone();
        context.userState = (IUserState) this.userState.clone();
        context.eventState = (IEventState) this.eventState.clone();
        context.bookingState = (IBookingState) this.bookingState.clone();
        context.sponsorshipState = (ISponsorshipState) this.sponsorshipState.clone();
        // clone attribute
        return context;
    }
     */


}
