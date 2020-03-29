import java.util.Comparator;

/**
 * Comparator to sort properties by their price.
 * 
 * @author Fahim Ahmed K1921959,
 * Issa Kabir K19014844,
 * Jehan Bhuyan K19017993,
 * Ork Hossain Muntaqin  K19016476
 */
public class PriceComparator implements Comparator<AirbnbListing> {

	/**
	 * Compare two properties by their number of price.
	 */
	@Override
	public int compare(AirbnbListing property1, AirbnbListing property2) {
		return property1.getPrice() - property2.getPrice();
	}

	@Override
	public String toString() {
		return "Price";
	}
	
}
