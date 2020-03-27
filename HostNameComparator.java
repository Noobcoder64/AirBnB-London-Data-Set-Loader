import java.util.Comparator;

public class HostNameComparator implements Comparator<AirbnbListing> {

	@Override
	public int compare(AirbnbListing property1, AirbnbListing property2) {
		return property1.getHost_name().compareTo(property2.getHost_name());
	}

	@Override
	public String toString() {
		return "Host name";
	}
	
}
