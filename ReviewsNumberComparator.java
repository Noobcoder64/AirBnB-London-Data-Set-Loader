import java.util.Comparator;

/**
 * Comparator to sort properties by their number of reviews.
 * 
 * @author Fahim Ahmed K1921959,
 * Issa Kabir K19014844,
 * Jehan Bhuyan K19017993,
 * Ork Hossain Muntaqin  K19016476
 */
public class ReviewsNumberComparator implements Comparator<AirbnbListing> {

	/**
	 * Compare two properties by their number number of reviews.
	 */
	@Override
	public int compare(AirbnbListing property1, AirbnbListing property2) {
		return property1.getNumberOfReviews() - property2.getNumberOfReviews();
	}

	@Override
	public String toString() {
		return "Number of reviews";
	}
	
}
