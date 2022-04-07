package model;

public class ConsumerPreferences extends Object {
    public boolean preferAirFiltration;
    public boolean preferOutdoorsOnly;
    public int preferredMaxCapacity;
    public int preferredMaxVenueSize;
    public boolean preferSocialDistancing;

    public ConsumerPreferences () {
        preferSocialDistancing = false;
        preferAirFiltration = false;
        preferOutdoorsOnly = false;
        preferredMaxCapacity = Integer.MAX_VALUE;
        preferredMaxVenueSize = Integer.MAX_VALUE;
    }

    @Override
    public String toString() {
        return "ConsumerPreferences{" +
                "preferAirFiltration=" + preferAirFiltration +
                ", preferOutdoorsOnly=" + preferOutdoorsOnly +
                ", preferredMaxCapacity=" + preferredMaxCapacity +
                ", preferredMaxVenueSize=" + preferredMaxVenueSize +
                ", preferSocialDistancing=" + preferSocialDistancing +
                '}';
    }
}
