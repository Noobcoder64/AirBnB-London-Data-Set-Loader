import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Collections;
import java.util.Comparator;

/**
 * A collection of properties that is within a give price range.
 * Takes in a list of properties and filters according to their price.
 * Also derives many statistical values from those properties.
 * 
 * @author Fahim Ahmed K1921959,
 * Issa Kabir K19014844,
 * Jehan Bhuyan K19017993,
 * Ork Hossain Muntaqin  K19016476
 */
public class Controller {

    private List<AirbnbListing> allProperties;

    private List<AirbnbListing> properties;

    private Map<String,Borough> boroughs;

    private StatisticCalculator boroughStatistics;

    private int startPrice;
    private int endPrice;

    private int averageReviews;
    private long totalNumberOfHomeOrApt;
    
    private Borough mostExpensiveBorough;
    private Borough cheapestBorough;
    private String mostReviewedBorough;
    private String cheapestPropertyDescription;
    private String mostExpensiveName;
    private String mostExpensiveHostName;
    private String cheapestHostName;
    
    private double reviewedNumber;
    private double latitudeOfCheapProperty;
    private double longitudeOfCheapProperty;
    private double latitudeOfExpensiveProperty;
    private double longitudeOfExpensiveProperty;

    public Controller(List<AirbnbListing> properties) {
    	allProperties = properties;
        boroughs = new HashMap<>();
    }

    /**
     * This method's use is to do the price range selection.
     */
    public void processRange() {
    	
        this.properties = allProperties.stream().filter(property -> property.getPrice() >= startPrice && property.getPrice() <= endPrice).collect(Collectors.toList());

        properties.stream().forEach(property -> {
                String neighbourhood = property.getNeighbourhood();

                if (!boroughs.containsKey(neighbourhood)) {
                    boroughs.put(neighbourhood, new Borough(neighbourhood));
                }
                boroughs.get(neighbourhood).addProperty(property);
        });
        
        // Average number of reviews per property.
        averageReviews = properties.stream().map(AirbnbListing::getNumberOfReviews).reduce(0, (a, b) -> a + b) / properties.size();
        
        // Number of entire home and apartments
        totalNumberOfHomeOrApt = properties.stream().map(AirbnbListing::getRoom_type).filter(rt -> rt.equals("Entire home/apt")).count();

        // Most Expensive Borough.
        mostExpensiveBorough = boroughs.values().stream().max(Comparator.comparing(Borough::getTotalPriceOfProperties)).orElse(null);
        
        // Cheapest Expensive Borough.
        cheapestBorough = boroughs.values().stream().min(Comparator.comparing(Borough::getTotalPriceOfProperties)).orElse(null);
        
        List<Integer> prices = new ArrayList<>();
        for (AirbnbListing property : properties) {
            if(property.getReviewsPerMonth() > reviewedNumber){
                reviewedNumber = property.getReviewsPerMonth();
                mostReviewedBorough = property.getNeighbourhood();
            }
            prices.add(property.getPrice() * property.getMinimumNights());
        }

        List<Integer> sortedlist = new ArrayList<>(prices);
        Collections.sort(sortedlist);

        for (AirbnbListing property : properties) 
        {
            if(sortedlist.get(0) == (property.getPrice() * property.getMinimumNights()))
            {
                cheapestPropertyDescription = property.getName();
                cheapestHostName = property.getHost_name();
                latitudeOfCheapProperty = property.getLatitude();
                longitudeOfCheapProperty = property.getLongitude();
            }
            if(sortedlist.get(sortedlist.size() - 1) == (property.getPrice() * property.getMinimumNights()))
            {
                mostExpensiveName = property.getName();
                mostExpensiveHostName = property.getHost_name();
                latitudeOfExpensiveProperty = property.getLatitude();;
                longitudeOfExpensiveProperty = property.getLongitude();;
            }
        }

        boroughStatistics = new StatisticCalculator();
        for (Borough borough : boroughs.values()) {
            boroughStatistics.addValue(borough.getNumberOfProperties());
        }

    }

    /**
     * Sets the starting price.
     * @param startPrice
     */
    public void setStartPrice(int startPrice) {
        this.startPrice = startPrice;
    }

    /**
     * Sets the ending price.
     * @param startPrice
     */
    public void setEndPrice(int endPrice) {
        this.endPrice = endPrice;
    }

    /**
     * Returns the boroughs.
     * @return the boroughs
     */
    public Map<String, Borough> getBoroughs() {
        return boroughs;
    }

    /**
     * Returns the statistics about a bourough.
     * @return the statistics about a bourough.
     */
    public StatisticCalculator getBoroughStatistics() {
        return boroughStatistics;
    }

    /**
     * Accessor Method to get number of properties available in selected price range
     */
    public int getAvailableProperties() {
        return properties.size();
    }

    /**
     * Accessor Method to get average reviews in the selected price range
     */
    public int getAverageReviews() {
        return averageReviews;
    }

    /**
     * Accessor Method to get total number of Home/Appartment type
     */
    public long getHomeApartments() {
        return totalNumberOfHomeOrApt;
    }

    /**
     * Accessor Method to get Borough with most expensive price
     */
    public Borough getMostExpensiveBorough() {
        return mostExpensiveBorough;
    }

    /**
     * Accessor Method to get Borough with cheapest price
     */
    public Borough getCheapestBorough() {
        return cheapestBorough;
    }

    /**
     * Accessor method to get Name/Property Description of the most expensive price
     */
    public String getMostExpensiveDescription() {
        return mostExpensiveName;
    }

    /**
     * Accessor method to get Name/Property Description of the cheapest price
     */
    public String getCheapestBoroughDescription() {
        return cheapestPropertyDescription;
    }

    /**
     * Accessor Method to get Borough with most reviewed
     */
    public String getMostReviewedBorough() {
        return mostReviewedBorough;
    }
    
    /**
     * Accessor Method to get Host Name with cheapest price
     */
    public String getCheapestHost() {
        return cheapestHostName;
    }

    /**
     * Accessor Method to get Host Name with most expensive price
     */
    public String getExpensiveHost() {
        return mostExpensiveHostName;
    }
    
    /**
     * Accessor Method to get latitude of cheapest property
     */
    public double getLatitudeOfCheapProperty() {
        return latitudeOfCheapProperty;
    }
    
    /**
     * Accessor Method to get longitude of cheapest property
     */
    public double getLongitudeOfCheapProperty() {
        return longitudeOfCheapProperty;
    }
    
    /**
     * Accessor Method to get latitude of expensive property
     */
    public double getLatitudeOfExpensiveProperty() {
        return latitudeOfExpensiveProperty;
    }
    
    /**
     * Accessor Method to get longitude of expensive property
     */
    public double getLongitudeOfExpensiveProperty() {
        return longitudeOfExpensiveProperty;
    }

}
