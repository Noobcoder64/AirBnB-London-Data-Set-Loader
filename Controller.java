import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Controller {
	
	AirbnbDataLoader airbnbDataLoader;
	
	private List<AirbnbListing> allProperties;
	
	private List<AirbnbListing> properties;
	
	private Map<String,Borough> boroughs;
	
	private StatisticCalculator boroughStatistics;
	
	private int startPrice;
	private int endPrice;
	
	public Controller() {
		airbnbDataLoader = new AirbnbDataLoader();
		airbnbDataLoader.load("airbnb-london.csv");
		
		boroughs = new HashMap<>();
		
		allProperties = airbnbDataLoader.getProperties();
	}
	
	public void processRange() {
		this.properties = allProperties.stream().filter(property -> property.getPrice() >= startPrice && property.getPrice() <= endPrice).collect(Collectors.toList());
		
		properties.stream().forEach(property -> {
			String neighbourhood = property.getNeighbourhood();
			
			if (!boroughs.containsKey(neighbourhood)) {
				boroughs.put(neighbourhood, new Borough(neighbourhood));
			}
			boroughs.get(neighbourhood).addProperty(property);
		
		});
		
		/*
		priceStatistics = new StatisticCalculator();
		for (AirbnbListing property : properties) {
			priceStatistics.addValue(property.getPrice());
		}
		*/
		
		boroughStatistics = new StatisticCalculator();
		
		for (Borough borough : boroughs.values()) {
			boroughStatistics.addValue(borough.getNumberOfProperties());
		}
	}
	
	public void setStartPrice(int startPrice) {
		this.startPrice = startPrice;
	}
	
	public void setEndPrice(int endPrice) {
		this.endPrice = endPrice;
	}
	
	public Map<String, Borough> getBoroughs() {
		return boroughs;
	}
	
	public StatisticCalculator getAllPriceStatistics() {
		return airbnbDataLoader.getPriceStatistics();
	}
	
	public StatisticCalculator getBoroughStatistics() {
		return boroughStatistics;
	}
	
}
