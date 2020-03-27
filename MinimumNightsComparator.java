import java.util.Comparator;

public class MinimumNightsComparator implements Comparator<AirbnbListing> {

	@Override
	public int compare(AirbnbListing property1, AirbnbListing property2) {
		return property1.getMinimumNights() - property2.getMinimumNights();
	}

	@Override
	public String toString() {
		return "Minimum number of nights";
	}
	
}
