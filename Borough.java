import java.util.ArrayList;
import java.util.List;

/**
 * Represents one borough on Airbnb.
 * A borough has properties for rental
 */ 
public class Borough {

	private String name;
	
	private List<AirbnbListing> properties;
	
	private StatisticCalculator priceStatistics;	// Statistics derived from the prices and minimum nights of a property.
	
	public Borough(String name) {
		this.name = name;
		this.properties = new ArrayList<>();
		
		for (AirbnbListing property : properties) {
			priceStatistics.addValue(property.getPrice() * property.getMinimumNights());
		}
	}
	
	/**
	 * Returns the name of the borough.
	 * @return the name of the borough
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the properties in this borough.
	 * @return the properties in this borough
	 */
	public List<AirbnbListing> getProperties() {
		return properties;
	}
	
	/**
	 * Adds a property in this borough.
	 * @param Property to be added
	 */
	public void addProperty(AirbnbListing property) {
		this.properties.add(property);
	}
	
	/**
	 * Returns the total number of properties in this borough.
	 * @return the total number of properties in this borough
	 */
	public int getNumberOfProperties() {
		return properties.size();
	}
	
	/**
	 * Returns the sum of the prices of the properties in this borough.
	 * @return the sum of the prices of the properties in this borough
	 */
	public int getTotalPriceOfProperties() {
		return priceStatistics.getTotal();
	}
	
}
