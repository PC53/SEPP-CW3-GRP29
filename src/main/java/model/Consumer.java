package model;

import java.util.List;

public class Consumer extends User{

    private List<Booking> bookings;

    private String name;
    private String phoneNumber;

    private ConsumerPreferences preferences;

    private ConsumerPreferences attribute;

    public Consumer(String name, String email, String phoneNumber, String password, String paymentAccountEmail) {
        super(email, password, paymentAccountEmail);
        this.name = name;
        this.phoneNumber = phoneNumber;

        preferences = new ConsumerPreferences();
        //bookings = new List<Booking>;
    }

    public void addBooking(Booking booking){
        bookings.add(booking);
    }

    public String getName() {
        return name;
    }

    public ConsumerPreferences getPreferences() {
        return preferences;
    }

    public void setPreferences(ConsumerPreferences preferences) {
        // not sure if this is implemented how the documentation intends.
        // you could individually access each attribute of preference and edit them directly.
        this.preferences = preferences;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void notify(String message) {
        System.out.print(message);
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
