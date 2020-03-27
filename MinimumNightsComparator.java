import java.util.Comparator;

/**
 * Comparator to sort properties by their number of minimum nights.
 */
public class MinimumNightsComparator implements Comparator<AirbnbListing> {

	/**
	 * Compare two properties by their number of minimum nights.
	 */
	@Override
	public int compare(AirbnbListing property1, AirbnbListing property2) {
		return property1.getMinimumNights() - property2.getMinimumNights();
	}

	@Override
	public String toString() {
		return "Minimum number of nights";
	}
	
}
