package model;

import external.EntertainmentProviderSystem;
import external.MockEntertainmentProviderSystem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EntertainmentProvider extends User{

    private List<Event> events;

    private String orgName;
    private String orgAddress;
    private String mainRepName;
    private String mainRepEmail;
    private List<String> otherRepNames;
    private List<String> otherRepEmails;
    private EntertainmentProviderSystem epSystem;


    public EntertainmentProvider(String orgName,
                          String orgAddress,
                          String paymentAccountEmail,
                          String mainRepName,
                          String mainRepEmail,
                          String password,
                          List<String> otherRepNames,
                          List<String> otherRepEmails)
    {
        super(mainRepEmail, password, paymentAccountEmail);

        this.orgName = orgName;
        this.orgAddress = orgAddress;
        this.mainRepName = mainRepName;
        this.mainRepEmail = mainRepEmail;
        this.otherRepNames = otherRepNames;
        this.otherRepEmails = otherRepEmails;
        this.epSystem = new MockEntertainmentProviderSystem(orgName, orgAddress);
        this.events = new ArrayList<>();
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgAddress() {
        return orgAddress;
    }

    public void setOrgAddress(String orgAddress) { this.orgAddress = orgAddress; }

    public List<Event> getEvents() {
        return events;
    }

    public void setMainRepName(String mainRepName) {
        this.mainRepName = mainRepName;
    }

    public void setMainRepEmail(String mainRepEmail) {
        this.mainRepEmail = mainRepEmail;
    }

    public void setOtherRepNames(List<String> otherRepNames) {
        this.otherRepNames = otherRepNames;
    }

    public void setOtherRepEmails(List<String> otherRepEmails) {
        this.otherRepEmails = otherRepEmails;
    }

    public EntertainmentProviderSystem getProviderSystem() {
        return epSystem;
    }

    @Override
    public String toString() {
        return "EntertainmentProvider{" +
                "events=" + events +
                ", orgName='" + orgName + '\'' +
                ", orgAddress='" + orgAddress + '\'' +
                ", mainRepName='" + mainRepName + '\'' +
                ", mainRepEmail='" + mainRepEmail + '\'' +
                ", otherRepNames=" + otherRepNames +
                ", otherRepEmails=" + otherRepEmails +
                '}';
    }
}
