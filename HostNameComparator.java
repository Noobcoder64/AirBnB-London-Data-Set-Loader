import java.util.Comparator;

/**
 * Comparator to sort properties by their host name.
 * 
 * @author Fahim Ahmed K1921959,
 * Issa Kabir K19014844,
 * Jehan Bhuyan K19017993,
 * Ork Hossain Muntaqin  K19016476
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
