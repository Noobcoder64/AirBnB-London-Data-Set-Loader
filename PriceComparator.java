import java.util.Comparator;

public class PriceComparator implements Comparator<AirbnbListing> {

	@Override
	public int compare(AirbnbListing property1, AirbnbListing property2) {
		return property1.getPrice() - property2.getPrice();
	}

	@Override
	public String toString() {
		return "Price";
	}
	
}
