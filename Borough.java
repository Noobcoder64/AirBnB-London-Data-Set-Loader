import java.util.ArrayList;
import java.util.List;

public class Borough {

	private String name;
	
	private List<AirbnbListing> properties;
	
	private StatisticCalculator priceStatistics;
	
	public Borough(String name) {
		this.name = name;
		this.properties = new ArrayList<>();
		
		for (AirbnbListing property : properties) {
			priceStatistics.addValue(property.getPrice() * property.getMinimumNights());
		}
	}
	
	public String getName() {
		return name;
	}
	
	public List<AirbnbListing> getProperties() {
		return properties;
	}
	
	public void addProperty(AirbnbListing property) {
		this.properties.add(property);
	}
	
	public int getNumberOfProperties() {
		return properties.size();
	}
	
	public int getTotalPriceOfProperties() {
		return priceStatistics.getTotal();
	}
	
}
