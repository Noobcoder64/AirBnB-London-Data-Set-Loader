import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Controller {
	
	AirbnbDataLoader airbnbDataLoader;
	
	private List<AirbnbListing> properties;
	
	private Map<String,Borough> boroughs;
	
	private StatisticCalculator priceStatistics;
	
	private StatisticCalculator boroughStatistics;
	
	public Controller(int startPrice, int endPrice) {
		airbnbDataLoader = new AirbnbDataLoader();
		boroughs = new HashMap<>();
		
		List<AirbnbListing> allProperties = airbnbDataLoader.getProperties();
		
		this.properties = allProperties.stream().filter(property -> property.getPrice() >= startPrice && property.getPrice() <= endPrice).collect(Collectors.toList());
		
		properties.stream().forEach(property -> {
			String neighbourhood = property.getNeighbourhood();
			
			if (!boroughs.containsKey(neighbourhood)) {
				boroughs.put(neighbourhood, new Borough(neighbourhood));
			}
			boroughs.get(neighbourhood).addProperty(property);
		
		});
		
		
		priceStatistics = new StatisticCalculator();
		for (AirbnbListing property : properties) {
			priceStatistics.addValue(property.getPrice());
		}
		
		boroughStatistics = new StatisticCalculator();
		
		for (Borough borough : boroughs.values()) {
			boroughStatistics.addValue(borough.getNumberOfProperties());
		}
		
	}
	
	public Map<String, Borough> getBoroughs() {
		return boroughs;
	}
	
	public StatisticCalculator getPriceStatistics() {
		return priceStatistics;
	}
	
	public StatisticCalculator getBoroughStatistics() {
		return boroughStatistics;
	}
	
}
