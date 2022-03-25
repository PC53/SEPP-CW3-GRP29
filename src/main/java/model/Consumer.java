package model;

import java.util.prefs.Preferences;

public class Consumer extends User{

    private Booking bookings;

    private String name;
    private String phoneNumber;

    private ConsumerPreferences preferences;

    private ConsumerPreferences attribute;

    Consumer(String name, String email, String phoneNumber, String password, String paymentAccountEmail) {
        super(email, password, paymentAccountEmail);
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public void addBooking(Booking booking){

    }

    public String getName() {
        return name;
    }

    public ConsumerPreferences getPreferences() {
        return preferences;
    }

    public void setPreferences(ConsumerPreferences preferences) {
        this.preferences = preferences;
    }

    public Booking getBookings() {
        return bookings;
    }

    public void notify(String message) {

    }

    public void setName(String newName) {
        this.name = newName;
    }

    public void setPhoneNumber(String newPhoneNumber) {
        this.phoneNumber = newPhoneNumber;
    }

    @Override
    public String toString() {
        return null;
    }

}
