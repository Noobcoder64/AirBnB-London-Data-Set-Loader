import java.util.Comparator;

/**
 * Comparator to sort properties by their host name.
 */
public class HostNameComparator implements Comparator<AirbnbListing> {

	/**
	 * Compare two properties by their host name lexicographically.
	 */
	@Override
	public int compare(AirbnbListing property1, AirbnbListing property2) {
		return property1.getHost_name().compareTo(property2.getHost_name());
	}

	@Override
	public String toString() {
		return "Host name";
	}
	
}
