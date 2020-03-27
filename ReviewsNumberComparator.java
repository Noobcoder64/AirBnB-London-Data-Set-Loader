import java.util.Comparator;

public class ReviewsNumberComparator implements Comparator<AirbnbListing> {

	@Override
	public int compare(AirbnbListing property1, AirbnbListing property2) {
		return property1.getNumberOfReviews() - property2.getNumberOfReviews();
	}

	@Override
	public String toString() {
		return "Number of reviews";
	}
	
}
